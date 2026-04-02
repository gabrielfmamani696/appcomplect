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

import com.google.firebase.dataconnect.QueryRef
import com.google.firebase.dataconnect.QueryResult
import com.google.firebase.dataconnect.serializers.UUIDSerializer
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer

public interface ObtenerRankingUsuarioQuery :
    GeneratedQuery<
      DefaultConnector,
      ObtenerRankingUsuarioQuery.Data,
      ObtenerRankingUsuarioQuery.Variables
    >
{
  
    @Serializable
  public data class Variables(
    val misEstrellas: Int
  )

    @Serializable
  public data class Data(
    val usuarios: List<UsuariosItem>
  ) {
    
        @Serializable
  public data class UsuariosItem(
    val id: @Serializable(with = UUIDSerializer::class) UUID
  )
      
  }
  

  public companion object {
    public val operationName: String = "ObtenerRankingUsuario"

    public val dataDeserializer: DeserializationStrategy<Data> =
      serializer()

    public val variablesSerializer: SerializationStrategy<Variables> =
      serializer()
  }
}

public fun ObtenerRankingUsuarioQuery.ref(
    misEstrellas: Int,
): QueryRef<
    ObtenerRankingUsuarioQuery.Data,
    ObtenerRankingUsuarioQuery.Variables
  > =
  ref(
      ObtenerRankingUsuarioQuery.Variables(
        misEstrellas=misEstrellas,
      )
  )

public suspend fun ObtenerRankingUsuarioQuery.execute(
    misEstrellas: Int,
  ): QueryResult<
    ObtenerRankingUsuarioQuery.Data,
    ObtenerRankingUsuarioQuery.Variables
  > =
  ref(
      misEstrellas=misEstrellas,
  ).execute()

  public fun ObtenerRankingUsuarioQuery.flow(
      misEstrellas: Int,
    ): Flow<ObtenerRankingUsuarioQuery.Data> =
    ref(
          misEstrellas=misEstrellas,
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }
