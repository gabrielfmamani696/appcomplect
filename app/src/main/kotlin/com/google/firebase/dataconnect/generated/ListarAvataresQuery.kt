
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


public interface ListarAvataresQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ListarAvataresQuery.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val opcionAvatars: List<OpcionAvatarsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class OpcionAvatarsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val imagenUrl: String,
    val descripcion: String
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListarAvatares"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun ListarAvataresQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    ListarAvataresQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ListarAvataresQuery.execute(

  

  ): com.google.firebase.dataconnect.QueryResult<
    ListarAvataresQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()


  public fun ListarAvataresQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<ListarAvataresQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

