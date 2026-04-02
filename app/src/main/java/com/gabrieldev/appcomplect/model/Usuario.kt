package com.gabrieldev.appcomplect.model

data class Usuario(
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val alias: String,
    val idAvatar: String,
    val numeroCelular: String = "",
    val curso: String? = null,
    val paralelo: String? = null,
    val nombreColegio: String? = null,
    val idRol: String = "",
    val nombreRol: String = "",
    val horaNotificacion: String? = null,
    val uuidSesion: String? = null,
    val estrellasPrestigio: Int = 0,
    val rachaActualDias: Int = 0,
    val avatarUrl: String = "",
    val idNivel: String = "",
    val nivel: Nivel? = null,
)

data class RolUsuario(
    val id: String,
    val nombreRol: String
)

data class InsigniaMini(
    val nombreVisible: String,
    val iconoRef: String
)

data class UsuarioLeaderboard(
    val id: String,
    val alias: String,
    val estrellasPrestigio: Int,
    val rachaActualDias: Int,
    val avatarUrl: String,
    val insignias: List<InsigniaMini>
)

data class SesionGuardada(
    val id: String,
    val alias: String,
    val avatarUrl: String
)
