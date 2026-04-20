package com.gabrieldev.appcomplect.data.local.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.gabrieldev.appcomplect.data.local.entidades.PreguntaEntity
import com.gabrieldev.appcomplect.data.local.entidades.RespuestaEntity

data class PreguntaRespuestas(

    @Embedded
    val pregunta: PreguntaEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "pregunta_id"
    )
    val respuestas: List<RespuestaEntity>
)