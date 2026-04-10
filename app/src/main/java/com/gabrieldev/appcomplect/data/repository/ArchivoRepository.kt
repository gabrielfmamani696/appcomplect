package com.gabrieldev.appcomplect.data.repository

import android.net.Uri
import com.gabrieldev.appcomplect.model.Archivo
import com.gabrieldev.appcomplect.model.ArchivoMetaEdicion
import com.gabrieldev.appcomplect.model.BorradorPreguntaDivulgacion
import com.gabrieldev.appcomplect.model.BorradorTarjetaDivulgacion
import com.gabrieldev.appcomplect.model.ContenidoArchivo
import com.gabrieldev.appcomplect.model.Cuestionario
import com.gabrieldev.appcomplect.model.PreguntaConRespuestas
import com.gabrieldev.appcomplect.model.RespuestaOpcion
import com.gabrieldev.appcomplect.model.Tarjeta
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.execute
import com.google.firebase.dataconnect.generated.ListarArchivosPublicosQuery
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.Date
import java.util.UUID

class ArchivoRepository(private val connector: DefaultConnector) {

    private val storage = FirebaseStorage.getInstance()

    suspend fun subirImagen(uriStr: String, folder: String): String? = withContext(Dispatchers.IO) {
        try {
            val uri = Uri.parse(uriStr)
            val fileName = "img_${UUID.randomUUID()}.jpg"
            val ref = storage.reference.child("archivos/$folder/$fileName")
            ref.putFile(uri).await()
            ref.downloadUrl.await().toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun mapItem(a: ListarArchivosPublicosQuery.Data.ArchivosItem): Archivo {
        return Archivo(
            idArchivo = a.id.toString(),
            titulo = a.titulo,
            descripcion = a.descripcion,
            imagenUrl = a.imagenUrl,
            fechaCreacion = a.fechaCreacion.seconds * 1000L,
            autor = a.usuario?.alias ?: "Desconocido",
            nivelRequerido = a.nivelRequerido?.jerarquia ?: 1,
            idAutor = a.usuario?.id?.toString()
        )
    }

    suspend fun obtenerArchivosDocentesAdmin(): List<Archivo> = withContext(Dispatchers.IO) {
        try {
            val result = connector.listarArchivosPublicos.execute()
            result.data.archivos.filter { a ->
                val rol = a.usuario?.rol?.nombreRol?.trim()
                rol.equals("Docente", ignoreCase = true) ||
                    rol.equals("Administrador", ignoreCase = true) ||
                    rol.equals("Admin", ignoreCase = true)
            }.map { mapItem(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerArchivosEstudiantesDivulgacion(): List<Archivo> = withContext(Dispatchers.IO) {
        try {
            val result = connector.listarArchivosPublicos.execute()
            result.data.archivos.filter { a ->
                val rol = a.usuario?.rol?.nombreRol.orEmpty()
                rol.contains("estudiante", ignoreCase = true)
            }.map { mapItem(it) }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerArchivoParaEdicion(idArchivo: String): ArchivoMetaEdicion? = withContext(Dispatchers.IO) {
        if (idArchivo.isBlank()) {
            System.err.println("ArchivoRepository: idArchivo está vacío en obtenerArchivoParaEdicion")
            return@withContext null
        }
        try {
            val uuid = try {
                UUID.fromString(idArchivo)
            } catch (e: IllegalArgumentException) {
                System.err.println("ArchivoRepository: idArchivo no es un UUID válido: '$idArchivo'")
                return@withContext null
            }

            val a = connector.obtenerArchivoParaEdicion.execute(id = uuid).data.archivo
                ?: return@withContext null
            ArchivoMetaEdicion(
                id = a.id.toString(),
                titulo = a.titulo,
                tema = a.tema,
                descripcion = a.descripcion,
                urlPortada = a.imagenUrl,
                idAutor = a.usuario?.id?.toString()
            )
        } catch (e: Exception) {
            System.err.println("ArchivoRepository: Error al obtener archivo para edición: ${e.message}")
            e.printStackTrace()
            null
        }
    }

    suspend fun obtenerContenidoArchivo(idArchivo: String): ContenidoArchivo? = withContext(Dispatchers.IO) {
        if (idArchivo.isBlank()) {
            System.err.println("ArchivoRepository: idArchivo está vacío en obtenerContenidoArchivo")
            return@withContext null
        }
        try {
            val uuid = try {
                UUID.fromString(idArchivo)
            } catch (e: IllegalArgumentException) {
                System.err.println("ArchivoRepository: idArchivo no es un UUID válido: '$idArchivo'")
                return@withContext null
            }

            val result = connector.obtenerContenidoArchivo.execute(idArchivo = uuid)
            val a = result.data.archivo ?: return@withContext null
            ContenidoArchivo(
                idArchivo = a.id,
                titulo = a.titulo,
                tarjetas = a.tarjetas_on_archivo.map { t ->
                    Tarjeta(
                        id = t.id,
                        ordenSecuencia = t.ordenSecuencia,
                        contenidoTexto = t.contenidoTexto,
                        tipoFondo = t.tipoFondo,
                        dataFondo = t.dataFondo
                    )
                },
                cuestionario = a.cuestionarios_on_archivo.firstOrNull()?.let { c ->
                    Cuestionario(
                        id = c.id,
                        tituloQuiz = c.tituloQuiz,
                        preguntas = c.preguntas_on_cuestionario.map { p ->
                            PreguntaConRespuestas(
                                id = p.id,
                                enunciado = p.enunciado,
                                respuestas = p.respuestas_on_pregunta.map { r ->
                                    RespuestaOpcion(
                                        id = r.id,
                                        textoOpcion = r.textoOpcion,
                                        esCorrecta = r.esCorrecta
                                    )
                                }
                            )
                        }
                    )
                }
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun limpiarContenidoArchivo(archivoId: UUID) = withContext(Dispatchers.IO) {
        try {
            val estructura = connector.obtenerEstructuraLimpiezaArchivo.execute(archivoId = archivoId).data.archivo
            estructura?.cuestionarios_on_archivo?.forEach { c ->
                c.preguntas_on_cuestionario.forEach { p ->
                    try {
                        connector.eliminarRespuestasDePregunta.execute(preguntaId = p.id)
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
                try {
                    connector.eliminarPreguntasDeCuestionario.execute(cuestionarioId = c.id)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
            try {
                connector.eliminarCuestionariosDeArchivo.execute(archivoId = archivoId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
            try {
                connector.eliminarTarjetasDeArchivo.execute(archivoId = archivoId)
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private suspend fun insertarTarjetasYCuestionario(
        archivoId: UUID,
        tarjetas: List<BorradorTarjetaDivulgacion>,
        tituloQuiz: String?,
        preguntas: List<BorradorPreguntaDivulgacion>
    ) {
        tarjetas.forEachIndexed { index, t ->
            connector.agregarTarjetaArchivo.execute(
                archivoId = archivoId,
                ordenSecuencia = index,
                contenidoTexto = t.contenidoTexto,
                tipoFondo = t.tipoFondo,
                dataFondo = t.dataFondo
            )
        }
        if (!tituloQuiz.isNullOrBlank() && preguntas.isNotEmpty()) {
            val cId = connector.agregarCuestionarioArchivo.execute(
                archivoId = archivoId,
                tituloQuiz = tituloQuiz
            ).data.cuestionario_insert.id
            for (preg in preguntas) {
                val pId = connector.agregarPreguntaCuestionario.execute(
                    cuestionarioId = cId,
                    enunciado = preg.enunciado
                ).data.pregunta_insert.id
                for (r in preg.respuestas) {
                    connector.agregarRespuestaPregunta.execute(
                        preguntaId = pId,
                        textoOpcion = r.textoOpcion,
                        esCorrecta = r.esCorrecta
                    )
                }
            }
        }
    }

    suspend fun crearArchivoDivulgacionCompleto(
        titulo: String,
        tema: String,
        descripcion: String,
        imagenPortada: String?,
        usuarioId: UUID,
        nivelRequeridoUuid: UUID?,
        tarjetas: List<BorradorTarjetaDivulgacion>,
        tituloQuiz: String?,
        preguntas: List<BorradorPreguntaDivulgacion>
    ): String? = withContext(Dispatchers.IO) {
        try {
            val nuevoId = connector.crearArchivoDivulgacion.execute(
                titulo = titulo,
                tema = tema,
                descripcion = descripcion,
                fechaCreacion = Timestamp(Date()),
                usuarioId = usuarioId
            ) {
                if (imagenPortada != null) imagenUrl = imagenPortada
                if (nivelRequeridoUuid != null) nivelRequeridoId = nivelRequeridoUuid
            }.data.archivo_insert.id
            insertarTarjetasYCuestionario(nuevoId, tarjetas, tituloQuiz, preguntas)
            nuevoId.toString()
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun actualizarArchivoDivulgacionCompleto(
        idArchivo: String,
        titulo: String,
        tema: String,
        descripcion: String,
        imagenPortada: String?,
        tarjetas: List<BorradorTarjetaDivulgacion>,
        tituloQuiz: String?,
        preguntas: List<BorradorPreguntaDivulgacion>
    ): Boolean = withContext(Dispatchers.IO) {
        try {
            val uuid = UUID.fromString(idArchivo)
            limpiarContenidoArchivo(uuid)
            connector.actualizarArchivoDivulgacion.execute(
                id = uuid,
                titulo = titulo,
                tema = tema,
                descripcion = descripcion
            ) {
                if (imagenPortada != null) imagenUrl = imagenPortada
            }
            insertarTarjetasYCuestionario(uuid, tarjetas, tituloQuiz, preguntas)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun eliminarArchivoDivulgacionCompleto(idArchivo: String): Boolean = withContext(Dispatchers.IO) {
        try {
            val uuid = UUID.fromString(idArchivo)
            limpiarContenidoArchivo(uuid)
            connector.eliminarArchivo.execute(id = uuid)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun primerNivelId(): UUID? = withContext(Dispatchers.IO) {
        try {
            connector.listarNiveles.execute().data.nivels
                .filter { it.jerarquia != null }
                .minByOrNull { it.jerarquia ?: Int.MAX_VALUE }?.id
        } catch (e: Exception) {
            null
        }
    }
}
