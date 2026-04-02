package com.gabrieldev.appcomplect.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gabrieldev.appcomplect.model.InsigniaMini
import com.gabrieldev.appcomplect.model.Nivel
import com.gabrieldev.appcomplect.model.RolUsuario
import com.gabrieldev.appcomplect.model.SesionGuardada
import com.gabrieldev.appcomplect.model.Usuario
import com.gabrieldev.appcomplect.model.UsuarioLeaderboard
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
import java.util.Date
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
                        val u = data.usuario ?: return@collect
                        _usuarioActivo.value = Usuario(
                            nombre = u.nombre,
                            apellidoPaterno = u.apellidoPaterno,
                            apellidoMaterno = u.apellidoMaterno,
                            alias = u.alias,
                            idAvatar = u.avatar?.id?.toString() ?: "",
                            uuidSesion = userId,
                            estrellasPrestigio = u.estrellasPrestigio,
                            rachaActualDias = u.rachaActualDias,
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
        val result = connector.crearUsuarioNuevo.execute(
            alias = usuario.alias,
            nombre = usuario.nombre,
            apellidoPaterno = usuario.apellidoPaterno,
            apellidoMaterno = usuario.apellidoMaterno,
            estadoValidacion = false,
            estrellasPrestigio = 0,
            rachaActualDias = 0,
            numeroCelular = usuario.numeroCelular,
            rolId = UUID.fromString(usuario.idRol),
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

        context.dataStore.edit { prefs ->
            prefs[USER_ID_KEY] = remoteId
        }

        _usuarioActivo.value = usuario.copy(uuidSesion = remoteId)
        iniciarSuscripcionPerfil(remoteId)
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
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
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
}
