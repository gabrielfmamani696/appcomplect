package com.gabrieldev.appcomplect.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gabrieldev.appcomplect.model.Usuario
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
                    tipoUsuario = 3,
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
                            idAvatar = u.avatar?.imagenUrl ?: "",
                            tipoUsuario = u.tipoUsuario,
                            uuidSesion = userId,
                            estrellasPrestigio = u.estrellasPrestigio,
                            rachaActualDias = u.rachaActualDias,
                            avatarUrl = u.avatar?.imagenUrl ?: "",
                            idNivel = u.nivel?.id?.toString() ?: "",
                            nivel = u.nivel?.let {
                                com.gabrieldev.appcomplect.model.Nivel(
                                    id = it.id.toString(),
                                    nombreRango = it.nombreRango,
                                    estrellasRequeridas = it.estrellasRequeridas,
                                    limitePalabrasTarjeta = it.limitePalabrasTarjeta
                                )
                            }
                        )
                    }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    @InternalSerializationApi
    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        return try {
            val result = connector.crearUsuarioNuevo.execute(
                alias = usuario.alias,
                nombre = usuario.nombre,
                apellidoPaterno = usuario.apellidoPaterno,
                apellidoMaterno = usuario.apellidoMaterno,
                estadoValidacion = false,
                estrellasPrestigio = 0,
                rachaActualDias = 0,
                tipoUsuario = usuario.tipoUsuario,
                ultimaActividad = Timestamp(Date())
            ) {
                avatarId = UUID.fromString(usuario.idAvatar)
                nivelId = UUID.fromString(usuario.idNivel)
            }

            val remoteId = result.data.usuario_insert.id.toString()

            context.dataStore.edit { prefs ->
                prefs[USER_ID_KEY] = remoteId
            }

            _usuarioActivo.value = usuario.copy(uuidSesion = remoteId)
            iniciarSuscripcionPerfil(remoteId)
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
}
