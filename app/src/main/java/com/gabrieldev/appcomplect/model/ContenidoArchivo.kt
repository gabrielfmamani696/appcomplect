package com.gabrieldev.appcomplect.model

import java.util.UUID

data class Tarjeta(
    val id: UUID,
    val ordenSecuencia: Int,
    val contenidoTexto: String,
    val tipoFondo: String,
    val dataFondo: String
)

data class RespuestaOpcion(
    val id: UUID,
    val textoOpcion: String,
    val esCorrecta: Boolean
)

data class PreguntaConRespuestas(
    val id: UUID,
    val enunciado: String,
    val respuestas: List<RespuestaOpcion>
)

data class Cuestionario(
    val id: UUID,
    val tituloQuiz: String,
    val preguntas: List<PreguntaConRespuestas>
)

data class ContenidoArchivo(
    val idArchivo: UUID,
    val titulo: String,
    val tarjetas: List<Tarjeta>,
    val cuestionario: Cuestionario?
)
