package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "archivos")
data class ArchivoEntity (

    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "titulo")
    val titulo: String,

    @ColumnInfo(name = "tema")
    val tema: String,

    @ColumnInfo(name = "descripcion")
    val descripcion: String,

    @ColumnInfo(name = "imagen_url")
    val imagenUrl: String,

    @ColumnInfo(name = "autor_original")
    val autorOriginal: String,

    @ColumnInfo(name = "licencia")
    val licencia: String
)