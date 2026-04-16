package com.gabrieldev.appcomplect.model

data class Archivo(
    val idArchivo: String,
    val titulo: String,
    val tema: String? = null,
    val descripcion: String,
    val imagenUrl: String?,
    val fechaCreacion: Long,
    val autor: String,
    val nivelRequerido: Int,
    val idUsuarioAutor: String? = null,
    val autorOriginal: String? = null,
    val licencia: String? = null,
    val espacioId: String? = null,
    val espacioNombre: String? = null
)

data class ArchivoMetaEdicion(
    val id: String,
    val titulo: String,
    val tema: String,
    val descripcion: String,
    val urlPortada: String?,
    val idAutor: String?,
    val autorOriginal: String? = null,
    val licencia: String? = null,
    val nivelRequeridoId: String? = null
)

data class BorradorTarjetaDivulgacion(
    val contenidoTexto: String,
    val tipoFondo: String,
    val dataFondo: String
)

data class BorradorRespuestaDivulgacion(
    val textoOpcion: String,
    val esCorrecta: Boolean
)

data class BorradorPreguntaDivulgacion(
    val enunciado: String,
    val respuestas: List<BorradorRespuestaDivulgacion>
)
