package com.gabrieldev.appcomplect.util

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.UUID

object FileDownloadManager {

    suspend fun descargarImagen(context: Context, urlImagen: String): String? = withContext(Dispatchers.IO) {
        if (urlImagen.isBlank()) return@withContext null
        if (!urlImagen.startsWith("http")) return@withContext urlImagen

        try {
            val url = URL(urlImagen)
            val connection = url.openConnection()
            connection.connect()

            val inputStream = connection.getInputStream()

            /**
             * carpeta del archivo
             */
            val directory = File(context.filesDir, "archivos_descargados")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            /**
             * direccion local de la imagen
             */
            val file = File(directory, "img_${UUID.randomUUID()}.jpg")
            val outputStream = FileOutputStream(file)

            val buffer = ByteArray(4096)
            var bytesRead: Int
            while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            outputStream.close()
            inputStream.close()

            return@withContext file.toURI().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
