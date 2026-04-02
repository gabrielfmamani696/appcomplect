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

public interface ObtenerLeaderboardQuery :
    GeneratedQuery<
      DefaultConnector,
      ObtenerLeaderboardQuery.Data,
      Unit
    >
{
  
    @Serializable
  public data class Data(
  
    val usuarios: List<UsuariosItem>
  ) {
    
        @Serializable
  public data class UsuariosItem(
  
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val alias: String,
    val estrellasPrestigio: Int,
    val rachaActualDias: Int,
    val avatar: Avatar?,
    val logroNotificados_on_usuario: List<LogroNotificadosOnUsuarioItem>
  ) {
    
        @Serializable
  public data class Avatar(
  
    val imagenUrl: String
  ) {
    
  }
      
        @Serializable
  public data class LogroNotificadosOnUsuarioItem(
  
    val insignia: Insignia?
  ) {
    
        @Serializable
  public data class Insignia(
  
    val iconoRef: String,
    val nombreVisible: String
  ) {
    
  }
      
  }
      
  }
      
  }
  
  public companion object {
    public val operationName: String = "ObtenerLeaderboard"

    public val dataDeserializer: DeserializationStrategy<Data> =
      serializer()

    public val variablesSerializer: SerializationStrategy<Unit> =
      serializer()
  }
}

public fun ObtenerLeaderboardQuery.ref(
  
): QueryRef<
    ObtenerLeaderboardQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ObtenerLeaderboardQuery.execute(
  
  ): QueryResult<
    ObtenerLeaderboardQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()

  public fun ObtenerLeaderboardQuery.flow(
    
    ): Flow<ObtenerLeaderboardQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }
