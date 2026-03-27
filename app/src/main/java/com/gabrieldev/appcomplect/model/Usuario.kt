package com.gabrieldev.appcomplect.model

data class Usuario(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val alias: String,
    val idAvatar: String,
    val tipoUsuario: Int,
    val uuidSesion: String? = null // Para control local
)
