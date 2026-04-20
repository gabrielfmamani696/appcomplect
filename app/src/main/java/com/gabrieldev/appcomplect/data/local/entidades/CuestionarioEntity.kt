package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "cuestionarios",
    foreignKeys = [
        ForeignKey(
            entity = ArchivoEntity::class,
            parentColumns = ["id"], // id  en Archivo
            childColumns = ["archivo_id"], // campo en esta entidad
            onDelete = ForeignKey.CASCADE // Si se borra el archivo, se borran sus tarjetas
        )
    ],
    indices = [Index(value = ["archivo_id"])]
)
data class CuestionarioEntity(
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "titulo_quiz")
    val tituloQuiz: String,

    @ColumnInfo(name = "archivo_id")
    val archivoId: String,
)