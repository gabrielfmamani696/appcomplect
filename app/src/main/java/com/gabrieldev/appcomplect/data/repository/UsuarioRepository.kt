package com.gabrieldev.appcomplect.data.repository

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
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
import java.util.UUID
import java.sql.Date as SqlDate

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

//intermedairio de datos del usuario e informacion relacionada
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
                        val u = data.usuario ?: return@collect
                        
                        val key = stringPreferencesKey("streak_data_$userId")
                        val prefs = context.dataStore.data.first()
                        val jsonStr = prefs[key] ?: "{}"
                        val obj = try { JSONObject(jsonStr) } catch(e: Exception) { JSONObject() }
                        
                        val pendiente = obj.optBoolean("pendienteSincronizar", false)
                        var rachaVisual = u.rachaActualDias

                        if (pendiente) {
                            rachaVisual = obj.optInt("rachaLocal", u.rachaActualDias)
                            scope.launch { sincronizarRachaConBackend() }
                        } else {
                            context.dataStore.edit { editPrefs ->
                                val editObj = try { JSONObject(editPrefs[key] ?: "{}") } catch(e: Exception) { JSONObject() }
                                editObj.put("rachaLocal", u.rachaActualDias)
                                val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                                val dateStr = format.format(u.ultimaActividad.toDate())
                                editObj.put("ultimaFechaAccion", dateStr)
                                editObj.put("pendienteSincronizar", false)
                                editPrefs[key] = editObj.toString()
                            }
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
            avatarId = UUID.fromString(usuario.idAvatar)
            nivelId = if(usuario.idNivel.isNotEmpty()) UUID.fromString(usuario.idNivel) else null
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
            
            val format = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val hoy = format.format(Date())
            val ultima = obj.optString("ultimaFechaAccion", "")
            var rachaLocal = obj.optInt("rachaLocal", _usuarioActivo.value?.rachaActualDias ?: 1)
            
            if (ultima != hoy) {
                if (ultima.isNotEmpty()) {
                    val ultimaDate = try { format.parse(ultima) } catch(e: Exception) { null }
                    if (ultimaDate != null) {
                        val calAyer = Calendar.getInstance()
                        calAyer.add(Calendar.DAY_OF_YEAR, -1)
                        if (format.format(ultimaDate) == format.format(calAyer.time)) {
                            rachaLocal += 1
                        } else {
                            rachaLocal = 1
                        }
                    } else {
                        rachaLocal = 1
                    }
                } else {
                     rachaLocal = 1
                }
                obj.put("ultimaFechaAccion", hoy)
                obj.put("rachaLocal", rachaLocal)
                obj.put("pendienteSincronizar", true)
                prefs[key] = obj.toString()
                
                _usuarioActivo.value = _usuarioActivo.value?.copy(rachaActualDias = rachaLocal)
            }
        }
        sincronizarRachaConBackend()
    }

    suspend fun sincronizarRachaConBackend() {
        val uuId = _usuarioActivo.value?.uuidSesion ?: return
        val key = stringPreferencesKey("streak_data_$uuId")
        val jsonStr = context.dataStore.data.first()[key] ?: return
        val obj = try { JSONObject(jsonStr) } catch(e: Exception) { return }
        
        if (obj.optBoolean("pendienteSincronizar", false)) {
            val racha = obj.optInt("rachaLocal")
            val ultimaActividadStr = obj.optString("ultimaFechaAccion")
            val actDate = try { SqlDate.valueOf(ultimaActividadStr) } catch(e: Exception) { Date() }
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
}
