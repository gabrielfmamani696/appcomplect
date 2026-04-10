
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


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface ObtenerLeaderboardQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerLeaderboardQuery.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuarios: List<UsuariosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class UsuariosItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val alias: String,
    val estrellasPrestigio: Int,
    val rachaActualDias: Int,
    val avatar: Avatar?,
    val logroNotificados_on_usuario: List<LogroNotificadosOnUsuarioItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Avatar(
  
    val imagenUrl: String
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class LogroNotificadosOnUsuarioItem(
  
    val insignia: Insignia?
  ) {
    
      
        @kotlinx.serialization.Serializable
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

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerLeaderboardQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerLeaderboardQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ObtenerLeaderboardQuery.execute(
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerLeaderboardQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()


  public fun ObtenerLeaderboardQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<ObtenerLeaderboardQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

