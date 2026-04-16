
@file:kotlin.Suppress(
  "KotlinRedundantDiagnosticSuppress",
  "LocalVariableName",
  "MayBeConstant",
  "RedundantVisibilityModifier",
  "RemoveEmptyClassBody",
  "SpellCheckingInspection",
  "LocalVariableName",
  "unused",
)

package com.google.firebase.dataconnect.generated


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface ListarTodasInsigniasQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ListarTodasInsigniasQuery.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val insignias: List<InsigniasItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class InsigniasItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreVisible: String,
    val descripcion: String,
    val iconoRef: String,
    val condicionDesbloqueo: String
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListarTodasInsignias"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun ListarTodasInsigniasQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    ListarTodasInsigniasQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ListarTodasInsigniasQuery.execute(
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListarTodasInsigniasQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()


  public fun ListarTodasInsigniasQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<ListarTodasInsigniasQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

