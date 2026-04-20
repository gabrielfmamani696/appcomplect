package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "preguntas",
    foreignKeys = [
        ForeignKey(
            entity = CuestionarioEntity::class,
            parentColumns = ["id"], // id  en Cuestionario
            childColumns = ["cuestionario_id"], // campo en esta entidad
            onDelete = ForeignKey.CASCADE // Si se borra el archivo, se borran sus tarjetas
        )
    ],
    indices = [Index(value = ["cuestionario_id"])]
)
data class PreguntaEntity (
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "enunciado")
    val enunciado: String,

    @ColumnInfo(name = "cuestionario_id")
    val cuestionarioId: String
)