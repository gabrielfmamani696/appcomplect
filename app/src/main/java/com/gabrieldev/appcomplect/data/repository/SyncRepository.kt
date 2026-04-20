package com.gabrieldev.appcomplect.data.repository

import com.gabrieldev.appcomplect.data.local.daos.IntentoDao
import com.gabrieldev.appcomplect.data.local.daos.UsuarioDao
import com.gabrieldev.appcomplect.data.local.entidades.UsuarioEntity
import com.gabrieldev.appcomplect.model.Usuario
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.ObtenerPerfilCompletoQuery
import com.google.firebase.dataconnect.generated.execute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

class SyncRepository(
    private val usuarioDao: UsuarioDao,
    private val intentoDao: IntentoDao,
    private val connector: DefaultConnector
) {

    /**
     * Sincroniza las estrellas de Prestigio del usuario con el servidor
     */
    suspend fun syncUsuario() = withContext(Dispatchers.IO) {
        val usuariosNoSync = usuarioDao.getUsuariosNoSincronizados()
        for (usuario in usuariosNoSync) {
            try {
                connector.actualizarEstrellasUsuario.execute(
                    id = UUID.fromString(usuario.id),
                    nuevasEstrellas = usuario.estrellasPrestigio
                )
                usuarioDao.marcarComoSincronizado(usuario.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun syncIntentos() = withContext(Dispatchers.IO) {
        val intentosNoSync = intentoDao.buscarIntentosNoSincronizados()
        for (intento in intentosNoSync) {
            try {
                connector.registrarIntentoExamen.execute(
                    usuarioId = UUID.fromString(intento.usuarioId),
                    archivoId = UUID.fromString(intento.archivoId),
                    calificacionObtenida = intento.calificacionObtenida,
                    fechaIntento = Timestamp(Date(intento.fechaIntento)),
                    completadoExitosamente = intento.completadoExitosamente
                )
                intentoDao.marcarComoSincronizado(intento.id)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    suspend fun syncAll() {
        syncUsuario()
        syncIntentos()
    }

}

//    @ColumnInfo(name = "curso")
//    val curso: String = "",
//
//    @ColumnInfo(name = "paralelo")
//    val paralelo: String = "",
//
//    @ColumnInfo(name = "nombre_colegio")
//    val nombreColegio: String = "",
//
//    @ColumnInfo(name = "nombre")
//    val nombre: String = "",
//
//    @ColumnInfo(name = "apellido_paterno")
//    val apellidoPaterno: String = "",
//
//    @ColumnInfo(name = "apellido_materno")
//    val apellidoMaterno: String = "",
//
//    @ColumnInfo(name = "sincronizado")
//    val sincronizado: Boolean
//)
