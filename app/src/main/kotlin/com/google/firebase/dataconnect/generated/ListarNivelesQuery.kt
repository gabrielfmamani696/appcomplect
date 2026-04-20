
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


public interface ListarNivelesQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ListarNivelesQuery.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val nivels: List<NivelsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class NivelsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreRango: String,
    val jerarquia: Int?,
    val estrellasRequeridas: Int,
    val limitePalabrasTarjeta: Int
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListarNiveles"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun ListarNivelesQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    ListarNivelesQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ListarNivelesQuery.execute(

  

  ): com.google.firebase.dataconnect.QueryResult<
    ListarNivelesQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()


  public fun ListarNivelesQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<ListarNivelesQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

