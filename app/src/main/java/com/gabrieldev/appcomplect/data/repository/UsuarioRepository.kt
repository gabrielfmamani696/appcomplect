package com.gabrieldev.appcomplect.data.repository

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.gabrieldev.appcomplect.model.Usuario
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.execute
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.serialization.InternalSerializationApi
import java.util.Date
import java.util.UUID

private val Context.dataStore by preferencesDataStore(name = "user_prefs")

class UsuarioRepository(
    private val connector: DefaultConnector,
    private val context: Context
) {
    private val _usuarioActivo = MutableStateFlow<Usuario?>(null)
    val usuarioActivo: StateFlow<Usuario?> = _usuarioActivo

    private val _estaCargando = MutableStateFlow(true)
    val estaCargando: StateFlow<Boolean> = _estaCargando

    private val USER_ID_KEY = stringPreferencesKey("session_user_id")

    suspend fun verificarSesion() {
        _estaCargando.value = true
        try {
            val userId = context.dataStore.data.map { prefs ->
                prefs[USER_ID_KEY]
            }.first()

            if (userId != null) {
                _usuarioActivo.value = Usuario(
                    nombre = "Sesión Recuperada",
                    apellidoPaterno = "",
                    apellidoMaterno = "",
                    alias = "Registrado",
                    idAvatar = "",
                    tipoUsuario = 3,
                    uuidSesion = userId
                )
            } else {
                _usuarioActivo.value = null
            }
        } catch (e: Exception) {
            _usuarioActivo.value = null
        } finally {
            _estaCargando.value = false
        }
    }

    @InternalSerializationApi
    suspend fun registrarUsuario(usuario: Usuario): Boolean {
        try {
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
            }

            val remoteId = result.data.usuario_insert.id.toString()

            context.dataStore.edit { prefs ->
                prefs[USER_ID_KEY] = remoteId
            }

            _usuarioActivo.value = usuario.copy(uuidSesion = remoteId)
            
            return true
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    suspend fun cerrarSesion() {
        context.dataStore.edit { prefs ->
            prefs.remove(USER_ID_KEY)
        }
        _usuarioActivo.value = null
    }
}
