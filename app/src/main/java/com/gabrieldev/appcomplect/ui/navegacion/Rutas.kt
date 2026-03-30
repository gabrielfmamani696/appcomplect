package com.gabrieldev.appcomplect.ui.navegacion

sealed class Rutas(val ruta: String) {
    object Inicio : Rutas("inicio")
    object Archivos : Rutas("archivos")
    object Perfil : Rutas("perfil")
}
