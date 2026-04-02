package com.gabrieldev.appcomplect.data.remote

import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.ObtenerUsuarioPorIdQuery
import com.google.firebase.dataconnect.generated.execute
import kotlinx.serialization.InternalSerializationApi
import java.util.UUID

@InternalSerializationApi
class UsuarioDataConnectRepository(
    private val connector: DefaultConnector
) {

    suspend fun guardarUsuario(
        alias: String,
        nombre: String,
        apellidoPaterno: String,
        apellidoMaterno: String,
        estadoValidacion: Boolean,
        estrellasPrestigio: Int,
        rachaActualDias: Int,
        numeroCelular: String,
        rolId: String,
        ultimaActividad: Timestamp
    ): Result<Unit> {
        return try {
            connector.crearUsuarioNuevo.execute(
                alias = alias,
                nombre = nombre,
                apellidoPaterno = apellidoPaterno,
                apellidoMaterno = apellidoMaterno,
                estadoValidacion = estadoValidacion,
                estrellasPrestigio = estrellasPrestigio,
                rachaActualDias = rachaActualDias,
                numeroCelular = numeroCelular,
                rolId = UUID.fromString(rolId),
                ultimaActividad = ultimaActividad
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    @InternalSerializationApi
    suspend fun obtenerUsuarioPorId(id: String): Result<ObtenerUsuarioPorIdQuery.Data.Usuario?> {
        return try {
            val result = connector.obtenerUsuarioPorId.execute(id = UUID.fromString(id))
            Result.success(result.data.usuario)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
