package com.gabrieldev.appcomplect.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gabrieldev.appcomplect.model.InsigniaMini
import com.gabrieldev.appcomplect.model.Nivel
import com.gabrieldev.appcomplect.model.RolUsuario
import com.gabrieldev.appcomplect.model.SesionGuardada
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.model.UsuarioLeaderboard
import com.gabrieldev.appcomplect.workers.RecordatorioReceiver
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.flow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.serialization.InternalSerializationApi
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.UUID

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UsuarioRepository(
    private val connector: DefaultConnector,
    private val context: Context
) {
    private val scope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    private val _usuarioActivo = MutableStateFlow<Usuario?>(null)
    val usuarioActivo: StateFlow<Usuario?> = _usuarioActivo

    private val _cargando = MutableStateFlow(true)
    val cargando: StateFlow<Boolean> = _cargando

    private val USER_ID_KEY = stringPreferencesKey("session_user_id")
    private val HISTORICAL_SESSIONS_KEY = stringPreferencesKey("historical_sessions")

    private val _sesionesGuardadas = MutableStateFlow<List<SesionGuardada>>(emptyList())
    val sesionesGuardadas: StateFlow<List<SesionGuardada>> = _sesionesGuardadas

    init {
        scope.launch {
            context.dataStore.data.map { prefs ->
                prefs[HISTORICAL_SESSIONS_KEY] ?: "[]"
            }.collect { jsonStr ->
                try {
                    val array = JSONArray(jsonStr)
                    val list = mutableListOf<SesionGuardada>()
                    for (i in 0 until array.length()) {
                        val obj = array.getJSONObject(i)
                        list.add(SesionGuardada(obj.getString("id"), obj.getString("alias"), obj.getString("avatarUrl")))
                    }
                    _sesionesGuardadas.value = list
                } catch (e: Exception) {
                    _sesionesGuardadas.value = emptyList()
                }
            }
        }
    }

    suspend fun verificarSesion() {
        _cargando.value = true
        try {
            val userId = context.dataStore.data.map { prefs ->
                prefs[USER_ID_KEY]
            }.first()

            if (userId != null) {
                _usuarioActivo.value = Usuario(
                    nombre = "",
                    apellidoPaterno = "",
                    apellidoMaterno = "",
                    alias = "...",
                    idAvatar = "",
                    uuidSesion = userId,
                    idNivel = "",
                    nivel = null
                )
                iniciarSuscripcionPerfil(userId)
            } else {
                _usuarioActivo.value = null
            }
        } catch (e: Exception) {
            _usuarioActivo.value = null
        } finally {
            _cargando.value = false
        }
    }

    private fun iniciarSuscripcionPerfil(userId: String) {
        scope.launch {
            try {
                connector.obtenerPerfilCompleto
                    .flow(id = UUID.fromString(userId))
                    .collect { data ->
                        //us remoto
                        val u = data.usuario ?: return@collect
                        //lectura local
                        val key = stringPreferencesKey("streak_data_$userId")
                        val prefs = context.dataStore.data.first()
                        val jsonStr = prefs[key] ?: "{}"
                        val obj = try { JSONObject(jsonStr) } catch(e: Exception) { JSONObject() }

                        //si pendiente == false => true
                        val pendiente = obj.optBoolean("pendienteSincronizar", false)
                        var rachaVisual = obj.optInt("rachaLocal", u.rachaActualDias).coerceAtLeast(1)
                        if (pendiente) {
                            if (rachaVisual <= 0) rachaVisual = u.rachaActualDias.coerceAtLeast(1)
                            scope.launch { sincronizarRachaConBackend() }
                        } else {
                            val fechaRemota = u.ultimaActividad?.toDate()?.let { fechaLocalDesdeDate(it) }
                            context.dataStore.edit { editPrefs ->
                                val editObj = try { JSONObject(editPrefs[key] ?: "{}") } catch(e: Exception) { JSONObject() }
                                editObj.put("rachaLocal", u.rachaActualDias)
                                fechaRemota?.let { editObj.put("ultimaFechaAccion", it) }
                                editObj.put("pendienteSincronizar", false)
                                editPrefs[key] = editObj.toString()
                            }
                            rachaVisual = u.rachaActualDias.coerceAtLeast(1)
                        }

                        _usuarioActivo.value = Usuario(
                            nombre = u.nombre,
                            apellidoPaterno = u.apellidoPaterno,
                            apellidoMaterno = u.apellidoMaterno,
                            alias = u.alias,
                            idAvatar = u.avatar?.id?.toString() ?: "",
                            uuidSesion = userId,
                            estrellasPrestigio = u.estrellasPrestigio,
                            rachaActualDias = rachaVisual,
                            avatarUrl = u.avatar?.imagenUrl ?: "",
                            curso = u.curso,
                            paralelo = u.paralelo,
                            nombreColegio = u.nombreColegio,
                            horaNotificacion = u.horaNotificacion,
                            idRol = u.rol.id.toString(),
                            idNivel = u.nivel?.id?.toString() ?: "",
                            nivel = u.nivel?.let {
                                Nivel(
                                    id = it.id.toString(),
                                    nombreRango = it.nombreRango,
                                    jerarquia = it.jerarquia ?: 1,
                                    estrellasRequeridas = it.estrellasRequeridas,
                                    limitePalabrasTarjeta = it.limitePalabrasTarjeta
                                )
                            },
                            numeroCelular = u.numeroCelular,
                            nombreRol = u.rol.nombreRol
                        )
                        registrarSesionHistorica(userId, u.alias, u.avatar?.imagenUrl ?: "")
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    private val fechaLocalFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.US).apply {
        timeZone = TimeZone.getDefault()
    }

    private fun fechaLocalHoy(): String {
        val calendario = Calendar.getInstance(TimeZone.getDefault())
        return fechaLocalFormatter.format(calendario.time)
    }

    private fun fechaLocalDesdeDate(fecha: Date): String = fechaLocalFormatter.format(fecha)

    private fun parseFechaLocal(fecha: String): Date? = try {
        fechaLocalFormatter.parse(fecha)
    } catch (e: Exception) {
        null
    }

    private fun obtenerInicioDelDia(fecha: Date): Date {
        val calendario = Calendar.getInstance(TimeZone.getDefault())
        calendario.time = fecha
        calendario.set(Calendar.HOUR_OF_DAY, 0)
        calendario.set(Calendar.MINUTE, 0)
        calendario.set(Calendar.SECOND, 0)
        calendario.set(Calendar.MILLISECOND, 0)
        return calendario.time
    }

    suspend fun obtenerRoles(): List<RolUsuario> {
        return try {
            val response = connector.listarRoles.execute()
            response.data.rolUsuarios.map {
                RolUsuario(
                    id = it.id.toString(),
                    nombreRol = it.nombreRol
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    @InternalSerializationApi
    suspend fun registrarUsuario(usuario: Usuario) {
        val rolesLocales = obtenerRoles()
        val rolMapeado = rolesLocales.find { it.id == usuario.idRol }
        val idEstudiante = rolesLocales.find { it.nombreRol.contains("estudiante", true) }?.id ?: usuario.idRol

        val niveles = obtenerTodosLosNiveles()
        val nivelJerarquia1 = niveles.find { it.jerarquia == 1 }
        val nivelIdFinal = nivelJerarquia1?.id ?: usuario.idNivel

        val result = connector.crearUsuarioNuevo.execute(
            alias = usuario.alias,
            nombre = usuario.nombre,
            apellidoPaterno = usuario.apellidoPaterno,
            apellidoMaterno = usuario.apellidoMaterno,
            estadoValidacion = false,
            estrellasPrestigio = 0,
            rachaActualDias = 0,
            numeroCelular = usuario.numeroCelular,
            rolId = UUID.fromString(idEstudiante),
            ultimaActividad = Timestamp(Date())
        ) {
            curso = usuario.curso
            paralelo = usuario.paralelo
            nombreColegio = usuario.nombreColegio
            horaNotificacion = "18:00"
            avatarId = if(usuario.idAvatar.isNotEmpty()) UUID.fromString(usuario.idAvatar) else null
            nivelId = if(nivelIdFinal.isNotEmpty()) UUID.fromString(nivelIdFinal) else null
        }

        val remoteId = result.data.usuario_insert.id.toString()

        if (rolMapeado != null && !rolMapeado.nombreRol.contains("estudiante", true)) {
            try {
                connector.crearSolicitudValidacion.execute(
                    usuarioId = UUID.fromString(remoteId),
                    rolSolicitadoId = UUID.fromString(usuario.idRol),
                    fechaEnvio = Timestamp(Date())
                )
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = remoteId
        }

        _usuarioActivo.value = usuario.copy(uuidSesion = remoteId, idRol = idEstudiante, nombreRol = "Estudiante")
        iniciarSuscripcionPerfil(remoteId)
        reprogramarNotificaciones("18:00")
    }

    suspend fun loginUsuario(numeroCelular: String, nombre: String, apellidoPaterno: String): Boolean {
        return try {
            val response = connector.obtenerUsuarioPorCredenciales.execute(numeroCelular, nombre, apellidoPaterno)
            if (response.data.usuarios.isNotEmpty()) {
                val u = response.data.usuarios.first()
                val sessionRemoteId = u.id.toString()
                context.dataStore.edit { prefs ->
                    prefs[USER_ID_KEY] = sessionRemoteId
                }
                verificarSesion()
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun actualizarUsuarioPerfil(usuario: Usuario): Boolean {
        return try {
            val sessionUuid = usuario.uuidSesion ?: throw Exception("Sin Sesion UUID")
            connector.actualizarUsuarioPerfil.execute(
                id = UUID.fromString(sessionUuid),
                alias = usuario.alias,
                nombre = usuario.nombre,
                apellidoPaterno = usuario.apellidoPaterno,
                apellidoMaterno = usuario.apellidoMaterno,
                numeroCelular = usuario.numeroCelular,
                rolId = UUID.fromString(usuario.idRol)
            ) {
                curso = usuario.curso
                paralelo = usuario.paralelo
                nombreColegio = usuario.nombreColegio
                horaNotificacion = usuario.horaNotificacion
                avatarId = if(usuario.idAvatar.isNotEmpty()) UUID.fromString(usuario.idAvatar) else null
            }
            
            _usuarioActivo.value = usuario
            reprogramarNotificaciones(usuario.horaNotificacion ?: "18:00")
            
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun reprogramarNotificaciones(hora: String) {
        val partes = hora.split(":")
        if (partes.size < 2) return
        val h = partes[0].toIntOrNull() ?: return
        val m = partes[1].toIntOrNull() ?: return
        
        val ahora = Calendar.getInstance()
        val target = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, h)
            set(Calendar.MINUTE, m)
            set(Calendar.SECOND, 0)
        }
        
        if (target.before(ahora)) {
            target.add(Calendar.DAY_OF_YEAR, 1)
        }

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, RecordatorioReceiver::class.java)
        
        val pendingIntent = PendingIntent.getBroadcast(
            context, 
            0, 
            intent, 
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            target.timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
        _usuarioActivo.value = null
    }

    private suspend fun registrarSesionHistorica(id: String, alias: String, avatarUrl: String) {
        context.dataStore.edit { prefs ->
            val jsonStr = prefs[HISTORICAL_SESSIONS_KEY] ?: "[]"
            try {
                val array = JSONArray(jsonStr)
                val arrNuevo = JSONArray()
                var encontrado = false
                for (i in 0 until array.length()) {
                    val obj = array.getJSONObject(i)
                    if (obj.getString("id") == id) {
                        val objNuevo = JSONObject()
                        objNuevo.put("id", id)
                        objNuevo.put("alias", alias)
                        objNuevo.put("avatarUrl", avatarUrl)
                        arrNuevo.put(objNuevo)
                        encontrado = true
                    } else {
                        arrNuevo.put(obj)
                    }
                }
                if (!encontrado) {
                    val objNuevo = JSONObject()
                    objNuevo.put("id", id)
                    objNuevo.put("alias", alias)
                    objNuevo.put("avatarUrl", avatarUrl)
                    arrNuevo.put(objNuevo)
                }
                prefs[HISTORICAL_SESSIONS_KEY] = arrNuevo.toString()
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }

    suspend fun iniciarSesionExistente(numeroCelular: String, nombre: String, apellidoPaterno: String): Boolean {
        return try {
            val response = connector.obtenerUsuarioPorCredenciales.execute(
                numeroCelular = numeroCelular,
                nombre = nombre,
                apellidoPaterno = apellidoPaterno
            )
            val users = response.data.usuarios
            if (users.isNotEmpty()) {
                val u = users[0]
                cambiarSesion(u.id.toString())
                true
            } else {
                false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun cambiarSesion(id: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = id
        }
        verificarSesion()
    }

    suspend fun obtenerLeaderboardTop5(): List<UsuarioLeaderboard> {
        return try {
            val response = connector.obtenerLeaderboard.execute()
            response.data.usuarios.map { u ->
                UsuarioLeaderboard(
                    id = u.id.toString(),
                    alias = u.alias,
                    estrellasPrestigio = u.estrellasPrestigio,
                    rachaActualDias = u.rachaActualDias,
                    avatarUrl = u.avatar?.imagenUrl ?: "",
                    insignias = u.logroNotificados_on_usuario.mapNotNull { ln ->
                        ln.insignia?.let { insignia ->
                            InsigniaMini(
                                nombreVisible = insignia.nombreVisible,
                                iconoRef = insignia.iconoRef
                            )
                        }
                    }
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerInvestigadoresOrdenadosPorEstrellas(): List<UsuarioLeaderboard> {
        return try {
            val response = connector.listarInvestigadoresOrdenadosPorEstrellas.execute()
            response.data.usuarios.map { u ->
                UsuarioLeaderboard(
                    id = u.id.toString(),
                    alias = u.alias,
                    estrellasPrestigio = u.estrellasPrestigio,
                    rachaActualDias = u.rachaActualDias,
                    avatarUrl = u.avatar?.imagenUrl ?: "",
                    insignias = emptyList()
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerRankingPosicion(estrellas: Int): Int {
        return try {
            val response = connector.obtenerRankingUsuario.execute(misEstrellas = estrellas)
            response.data.usuarios.size + 1
        } catch (e: Exception) {
            e.printStackTrace()
            0
        }
    }

    suspend fun registrarAccionDiaria() {
        val uuId = _usuarioActivo.value?.uuidSesion ?: return
        val key = stringPreferencesKey("streak_data_$uuId")
        context.dataStore.edit { prefs ->
            val jsonStr = prefs[key] ?: "{}"
            val obj = try { JSONObject(jsonStr) } catch(e: Exception) { JSONObject() }

                val hoy = fechaLocalHoy()
            val ultimaFecha = obj.optString("ultimaFechaAccion", "")
            val actualRacha = obj.optInt("rachaLocal", _usuarioActivo.value?.rachaActualDias ?: 1).coerceAtLeast(1)
            val ayer = Calendar.getInstance(TimeZone.getDefault()).apply {
                add(Calendar.DAY_OF_YEAR, -1)
            }
            val ayerStr = fechaLocalFormatter.format(ayer.time)
            val nuevaRacha = when {
                ultimaFecha == hoy -> actualRacha
                ultimaFecha == ayerStr -> actualRacha + 1
                else -> 1
            }

            if (ultimaFecha != hoy) {
                obj.put("ultimaFechaAccion", hoy)
                obj.put("rachaLocal", nuevaRacha)
                obj.put("pendienteSincronizar", true)
                prefs[key] = obj.toString()

                _usuarioActivo.value = _usuarioActivo.value?.copy(rachaActualDias = nuevaRacha)
            }
        }
        scope.launch {
            sincronizarRachaConBackend()
        }
    }

    suspend fun sincronizarRachaConBackend() {
        val uuId = _usuarioActivo.value?.uuidSesion ?: return
        val key = stringPreferencesKey("streak_data_$uuId")
        val jsonStr = context.dataStore.data.first()[key] ?: return
        val obj = try { JSONObject(jsonStr) } catch(e: Exception) { return }

        if (obj.optBoolean("pendienteSincronizar", false)) {
            val racha = obj.optInt("rachaLocal").coerceAtLeast(1)
            val ultimaActividadStr = obj.optString("ultimaFechaAccion")
            val fechaLocal = parseFechaLocal(ultimaActividadStr) ?: Date()
            val actDate = obtenerInicioDelDia(fechaLocal)
            try {
                connector.actualizarRachaUsuario.execute(
                    id = UUID.fromString(uuId),
                    racha = racha,
                    ultimaActividad = Timestamp(actDate)
                )
                context.dataStore.edit { prefs ->
                     val eObj = try { JSONObject(prefs[key] ?: "{}") } catch(e: Exception) { JSONObject() }
                     eObj.put("pendienteSincronizar", false)
                     prefs[key] = eObj.toString()
                }
            } catch (e: Exception) {
               e.printStackTrace()
            }
        }
    }

    suspend fun obtenerTodosLosNiveles(): List<Nivel> {
        return try {
            connector.listarNiveles.execute().data.nivels
                .filter { it.jerarquia != null }
                .sortedBy { it.jerarquia }
                .map { n ->
                    Nivel(
                        id = n.id.toString(),
                        nombreRango = n.nombreRango,
                        jerarquia = n.jerarquia ?: 1,
                        estrellasRequeridas = n.estrellasRequeridas,
                        limitePalabrasTarjeta = n.limitePalabrasTarjeta
                    )
                }
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun registrarResultadoExamen(
        usuarioId: String,
        archivoId: String,
        calificacion: Int,
        estrellasGanadas: Int
    ) {
        try {
            val aprobado = calificacion >= 60
            val uuid = UUID.fromString(usuarioId)
            connector.registrarIntentoExamen.execute(
                usuarioId = uuid,
                archivoId = UUID.fromString(archivoId),
                calificacionObtenida = calificacion,
                fechaIntento = Timestamp(Date()),
                completadoExitosamente = aprobado
            )
            if (estrellasGanadas > 0) {
                val usuarioActual = _usuarioActivo.value ?: return
                val nuevasEstrellas = usuarioActual.estrellasPrestigio + estrellasGanadas
                connector.actualizarEstrellasUsuario.execute(
                    id = uuid,
                    nuevasEstrellas = nuevasEstrellas
                )
                val nivelActualizado = verificarSubidaNivel(uuid, nuevasEstrellas, usuarioActual.nivel?.id ?: "")
                _usuarioActivo.value = usuarioActual.copy(
                    estrellasPrestigio = nuevasEstrellas,
                    nivel = nivelActualizado ?: usuarioActual.nivel
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun verificarSubidaNivel(
        usuarioUuid: UUID,
        nuevasEstrellas: Int,
        nivelActualId: String
    ): Nivel? {
        return try {
            val sorted = connector.listarNiveles.execute().data.nivels
                .filter { it.jerarquia != null }
                .sortedBy { it.jerarquia }

            if (sorted.isEmpty()) return null

            val idxActual = sorted.indexOfFirst { it.id.toString() == nivelActualId }
            if (idxActual < 0) {
                Log.w("NivelCheck", "ID de nivel actual no encontrado en la lista: $nivelActualId")
                return null
            }

            Log.d("NivelCheck",
                "Estrellas: $nuevasEstrellas | NivelActual: ${sorted[idxActual].nombreRango} (jerarquia=${sorted[idxActual].jerarquia}) | Total niveles: ${sorted.size}"
            )
            sorted.forEach { n ->
                Log.d("NivelCheck",
                    "  -> ${n.nombreRango} | jerarquia=${n.jerarquia} | estrellasRequeridas=${n.estrellasRequeridas}"
                )
            }

            var idxObjetivo = idxActual
            while (idxObjetivo < sorted.size - 1) {
                val nivelEval = sorted[idxObjetivo]
                if (nuevasEstrellas >= nivelEval.estrellasRequeridas) {
                    idxObjetivo++
                } else {
                    break
                }
            }

            if (idxObjetivo > idxActual) {
                val nivelNuevo = sorted[idxObjetivo]
                Log.d("NivelCheck", "Subiendo a: ${nivelNuevo.nombreRango} (jerarquia=${nivelNuevo.jerarquia})")
                connector.actualizarNivelUsuario.execute(
                    id = usuarioUuid,
                    nivelId = nivelNuevo.id
                )
                Nivel(
                    id = nivelNuevo.id.toString(),
                    nombreRango = nivelNuevo.nombreRango,
                    jerarquia = nivelNuevo.jerarquia ?: 1,
                    estrellasRequeridas = nivelNuevo.estrellasRequeridas,
                    limitePalabrasTarjeta = nivelNuevo.limitePalabrasTarjeta
                )
            } else {
                Log.d("NivelCheck", "Sin subida de nivel.")
                null
            }
        } catch (e: Exception) {
            Log.e("NivelCheck", "Error: ${e.message}", e)
            null
        }
    }
}
