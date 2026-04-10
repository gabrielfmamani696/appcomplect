package com.gabrieldev.appcomplect.ui.navegacion

sealed class Rutas(val ruta: String) {
    object Inicio : Rutas("inicio")
    object Archivos : Rutas("archivos")
    object Perfil : Rutas("perfil")
    object ArchivoDetalle : Rutas("archivo_detalle/{idArchivo}") {
        fun conId(id: String) = "archivo_detalle/$id"
    }
    object ArchivoCrearDivulgacion : Rutas("archivo_crear_divulgacion")
    object ArchivoEditarDivulgacion : Rutas("archivo_editar_divulgacion/{idArchivo}") {
        fun conId(id: String) = "archivo_editar_divulgacion/$id"
    }
}
