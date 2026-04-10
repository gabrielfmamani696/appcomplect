package com.gabrieldev.appcomplect.util

object UsuarioUtils {
    fun generarAlias(nombre: String): String {
        if (nombre.isBlank()) return ""
        val randomNum = (1000..9999).random()
        val base = nombre.trim().lowercase().take(3)
        return "$base$randomNum"
    }
}
