@file:Suppress(
  "KotlinRedundantDiagnosticSuppress",
  "LocalVariableName",
  "MayBeConstant",
  "RedundantVisibilityModifier",
  "RedundantCompanionReference",
  "RemoveEmptyClassBody",
  "SpellCheckingInspection",
  "LocalVariableName",
  "unused",
)

package com.google.firebase.dataconnect.generated

import com.google.firebase.Timestamp
import com.google.firebase.dataconnect.QueryRef
import com.google.firebase.dataconnect.QueryResult
import com.google.firebase.dataconnect.serializers.TimestampSerializer
import com.google.firebase.dataconnect.serializers.UUIDSerializer
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer

public interface ObtenerPerfilCompletoQuery :
    GeneratedQuery<
      DefaultConnector,
      ObtenerPerfilCompletoQuery.Data,
      ObtenerPerfilCompletoQuery.Variables
    >
{
  
    @Serializable
  public data class Variables(
    val id: @Serializable(with = UUIDSerializer::class) UUID
  )

    @Serializable
  public data class Data(
    val usuario: Usuario?
  ) {
    
        @Serializable
  public data class Usuario(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val alias: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val estadoValidacion: Boolean,
    val estrellasPrestigio: Int,
    val rachaActualDias: Int,
    val numeroCelular: String,
    val curso: String?,
    val paralelo: String?,
    val nombreColegio: String?,
    val horaNotificacion: String?,
    val rol: Rol,
    val ultimaActividad: @Serializable(with = TimestampSerializer::class) Timestamp,
    val nivel: Nivel?,
    val avatar: Avatar?
  ) {
    
        @Serializable
  public data class Rol(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val nombreRol: String
  )

        @Serializable
  public data class Nivel(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val nombreRango: String,
    val estrellasRequeridas: Int,
    val limitePalabrasTarjeta: Int
  )

        @Serializable
  public data class Avatar(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val imagenUrl: String,
    val descripcion: String
  )
      
  }
  }

  public companion object {
    public val operationName: String = "ObtenerPerfilCompleto"

    public val dataDeserializer: DeserializationStrategy<Data> =
      serializer()

    public val variablesSerializer: SerializationStrategy<Variables> =
      serializer()
  }
}

public fun ObtenerPerfilCompletoQuery.ref(
    id: UUID,
): QueryRef<
    ObtenerPerfilCompletoQuery.Data,
    ObtenerPerfilCompletoQuery.Variables
  > =
  ref(
      ObtenerPerfilCompletoQuery.Variables(
        id=id,
      )
  )

public suspend fun ObtenerPerfilCompletoQuery.execute(
    id: UUID,
  ): QueryResult<
    ObtenerPerfilCompletoQuery.Data,
    ObtenerPerfilCompletoQuery.Variables
  > =
  ref(
      id=id,
  ).execute()

  public fun ObtenerPerfilCompletoQuery.flow(
      id: UUID,
    ): Flow<ObtenerPerfilCompletoQuery.Data> =
    ref(
          id=id,
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }
