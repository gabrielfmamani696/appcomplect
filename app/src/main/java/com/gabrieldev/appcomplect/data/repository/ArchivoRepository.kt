package com.gabrieldev.appcomplect.data.repository

import android.net.Uri
import com.gabrieldev.appcomplect.model.Archivo
import com.gabrieldev.appcomplect.model.ArchivoMetaEdicion
import com.gabrieldev.appcomplect.model.BorradorPreguntaDivulgacion
import com.gabrieldev.appcomplect.model.BorradorTarjetaDivulgacion
import com.gabrieldev.appcomplect.model.ContenidoArchivo
import com.gabrieldev.appcomplect.model.Cuestionario
import com.gabrieldev.appcomplect.model.EspacioAprendizaje
import com.gabrieldev.appcomplect.model.EstadisticasArchivo
import com.gabrieldev.appcomplect.model.IntentoEstudiante
import com.gabrieldev.appcomplect.model.PreguntaConRespuestas
import com.gabrieldev.appcomplect.model.RespuestaOpcion
import com.gabrieldev.appcomplect.model.Tarjeta
import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.generated.DefaultConnector
import com.google.firebase.dataconnect.generated.ListarArchivosPublicosQuery
import com.google.firebase.dataconnect.generated.execute
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
            tema = a.tema,
            descripcion = a.descripcion,
            imagenUrl = a.imagenUrl,
            fechaCreacion = a.fechaCreacion.seconds * 1000L,
            autor = a.usuario?.alias ?: "Desconocido",
            nivelRequerido = a.nivelRequerido?.jerarquia ?: 1,
            idUsuarioAutor = a.usuario?.id?.toString(),
            autorOriginal = a.autorOriginal,
            licencia = a.licencia,
            espacioId = a.espacio?.id?.toString(),
            espacioNombre = a.espacio?.nombreEspacio
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
                idAutor = a.usuario?.id?.toString(),
                autorOriginal = a.autorOriginal,
                licencia = a.licencia,
                nivelRequeridoId = a.nivelRequerido?.id?.toString()
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
                tema = a.tema,
                descripcion = a.descripcion,
                fechaCreacion = a.fechaCreacion.seconds * 1000L,
                imagenUrl = a.imagenUrl,
                autorOriginal = a.autorOriginal,
                licencia = a.licencia,
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
        autorOriginal: String?,
        licencia: String?,
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
                this.autorOriginal = autorOriginal
                this.licencia = licencia
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
        nivelRequeridoUuid: UUID?,
        autorOriginal: String?,
        licencia: String?,
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
                if (nivelRequeridoUuid != null) nivelRequeridoId = nivelRequeridoUuid
                this.autorOriginal = autorOriginal
                this.licencia = licencia
            }
            insertarTarjetasYCuestionario(uuid, tarjetas, tituloQuiz, preguntas)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun actualizarEspacioArchivo(archivoId: String, espacioId: String?): Boolean = withContext(Dispatchers.IO) {
        try {
            val uuid = UUID.fromString(archivoId)
            val espacioUuid = espacioId?.let { UUID.fromString(it) }
            connector.actualizarArchivoEspacio.execute(
                id = uuid
            ) {
                this.espacioId = espacioUuid
            }
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

    suspend fun crearEspacioAprendizaje(usuarioId: UUID, nombre: String): String? = withContext(Dispatchers.IO) {
        var intentos = 0
        val maxIntentos = 5
        var exito = false
        var codigoFinal: String? = null

        while (intentos < maxIntentos && !exito) {
            val codigoPrueba = UUID.randomUUID().toString()
                .replace("-", "")
                .take(6)
                .uppercase()
            
            try {
                connector.crearEspacioAprendizaje.execute(
                    usuarioId = usuarioId,
                    nombreEspacio = nombre,
                    codigoAcceso = codigoPrueba
                )
                exito = true
                codigoFinal = codigoPrueba
            } catch (e: Exception) {
                intentos++
                if (intentos >= maxIntentos) {
                    e.printStackTrace()
                }
            }
        }
        codigoFinal
    }

    suspend fun buscarEspacioPorCodigo(codigoAcceso: String): EspacioAprendizaje? = withContext(Dispatchers.IO) {
        try {
            val result = connector.buscarEspacioPorCodigo.execute(codigoAcceso = codigoAcceso)
            result.data.espacioAprendizajes.firstOrNull()?.let {
                EspacioAprendizaje(
                    id = it.id.toString(),
                    nombre = it.nombreEspacio,
                    codigoAcceso = it.codigoAcceso
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun unirseAEspacio(usuarioId: UUID, espacioId: UUID): Boolean = withContext(Dispatchers.IO) {
        try {
            connector.unirseAEspacio.execute(
                usuarioId = usuarioId,
                espacioId = espacioId
            )
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    suspend fun obtenerMisEspacios(usuarioId: UUID): List<EspacioAprendizaje> = withContext(Dispatchers.IO) {
        try {
            val espaciosCreados = connector.obtenerMisEspacios.execute(usuarioId = usuarioId).data.espacioAprendizajes.map {
                EspacioAprendizaje(
                    id = it.id.toString(),
                    nombre = it.nombreEspacio,
                    codigoAcceso = it.codigoAcceso
                )
            }
            val espaciosMiembro = connector.obtenerEspaciosPorMiembro.execute(usuarioId = usuarioId).data.miembroEspacios.mapNotNull {
                it.espacio?.let { espacio ->
                    EspacioAprendizaje(
                        id = espacio.id.toString(),
                        nombre = espacio.nombreEspacio,
                        codigoAcceso = espacio.codigoAcceso
                    )
                }
            }
            (espaciosCreados + espaciosMiembro).distinctBy { it.id }
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    suspend fun obtenerEstadisticasArchivo(archivoId: UUID): EstadisticasArchivo? = withContext(Dispatchers.IO) {
        try {
            val intentos = connector.obtenerEstadisticasArchivo.execute(archivoId = archivoId).data.intentos
            if (intentos.isEmpty()) {
                return@withContext EstadisticasArchivo(0, 0, emptyList())
            }

            val detalles = intentos.map {
                IntentoEstudiante(
                    nombreEstudiante = "${it.usuario?.nombre} ${it.usuario?.apellidoPaterno}",
                    calificacion = it.calificacionObtenida
                )
            }
            val promedio = detalles.map { it.calificacion }.average().toInt()

            EstadisticasArchivo(
                promedioGeneral = promedio,
                totalIntentos = detalles.size,
                detallesEstudiantes = detalles
            )
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}
