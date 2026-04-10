package com.gabrieldev.appcomplect.data.repository

import com.gabrieldev.appcomplect.model.OpcionAvatar
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.execute

class AvatarRepository(private val connector: DefaultConnector) {

    suspend fun obtenerAvatares(): List<OpcionAvatar> {
        return try {
            val result = connector.listarAvatares.execute()
            result.data.opcionAvatars.map {
                OpcionAvatar(
                    idAvatar = it.id.toString(),
                    descripcion = it.descripcion,
                    urlImagen = it.imagenUrl
                )
            }
        } catch (e: Exception) {
            emptyList()
        }
    }
}
