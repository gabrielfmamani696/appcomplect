package com.gabrieldev.appcomplect.model

data class Usuario(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val alias: String,
    val idAvatar: String,
    val tipoUsuario: Int,
    val uuidSesion: String? = null,
    val estrellasPrestigio: Int = 0,
    val rachaActualDias: Int = 0,
    val avatarUrl: String = "",
    val idNivel: String = "",
    val nivel: Nivel? = null,
)
