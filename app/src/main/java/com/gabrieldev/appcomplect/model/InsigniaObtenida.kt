package com.gabrieldev.appcomplect.model

data class InsigniaObtenida(
    val id: String,
    val nombreVisible: String,
    val descripcion: String,
    val iconoRef: String,
    val condicionDesbloqueo: String
)

object InsigniaSlug {
    const val PRIMER_CUESTIONARIO   = "PRIMER_CUESTIONARIO"
    const val PRIMER_ARTICULO       = "PRIMER_ARTICULO"
    const val PERFECCIONISTA        = "PERFECCIONISTA"
    const val RACHA_1_DIA           = "RACHA_1_DIA"
    const val RACHA_3_DIAS          = "RACHA_3_DIAS"
    const val RACHA_7_DIAS          = "RACHA_7_DIAS"
}
