package com.gabrieldev.appcomplect.data.local.entidades

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "respuestas",
    foreignKeys = [
        ForeignKey(
            entity = PreguntaEntity::class,
            parentColumns = ["id"], // id  en Pregunta
            childColumns = ["pregunta_id"], // campo en esta entidad
            onDelete = ForeignKey.CASCADE // Si se borra el archivo, se borran sus tarjetas
        )
    ],
    indices = [Index(value = ["pregunta_id"])]
)
data class RespuestaEntity (
    @PrimaryKey
    val id: String,

    @ColumnInfo(name = "texto_opcion")
    val textoOpcion: String,

    @ColumnInfo(name = "es_correcta")
    val esCorrecta: Boolean,

    @ColumnInfo(name = "pregunta_id")
    val preguntaId: String
)