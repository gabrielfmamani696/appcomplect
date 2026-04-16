package com.gabrieldev.appcomplect.model

data class EspacioAprendizaje(
    val id: String,
    val nombre: String,
    val codigoAcceso: String
)

data class IntentoEstudiante(
    val nombreEstudiante: String,
    val calificacion: Int
)

data class EstadisticasArchivo(
    val promedioGeneral: Int,
    val totalIntentos: Int,
    val detallesEstudiantes: List<IntentoEstudiante>
)
