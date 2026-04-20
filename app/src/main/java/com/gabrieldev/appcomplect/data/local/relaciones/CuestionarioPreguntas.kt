package com.gabrieldev.appcomplect.data.local.relaciones

import androidx.room.Embedded
import androidx.room.Relation
import com.gabrieldev.appcomplect.data.local.entidades.CuestionarioEntity
import com.gabrieldev.appcomplect.data.local.entidades.PreguntaEntity

data class CuestionarioPreguntas (

    @Embedded
    val cuestionario: CuestionarioEntity,

    @Relation(
        entity = PreguntaEntity::class,
        parentColumn = "id",
        entityColumn = "cuestionario_id"
    )
    val respuestas: List<PreguntaRespuestas>
)