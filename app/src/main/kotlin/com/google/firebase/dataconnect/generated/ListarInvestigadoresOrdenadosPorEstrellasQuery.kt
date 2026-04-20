
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


public interface ListarInvestigadoresOrdenadosPorEstrellasQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ListarInvestigadoresOrdenadosPorEstrellasQuery.Data,
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
    val avatar: Avatar?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Avatar(
  
    val imagenUrl: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListarInvestigadoresOrdenadosPorEstrellas"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun ListarInvestigadoresOrdenadosPorEstrellasQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    ListarInvestigadoresOrdenadosPorEstrellasQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ListarInvestigadoresOrdenadosPorEstrellasQuery.execute(

  

  ): com.google.firebase.dataconnect.QueryResult<
    ListarInvestigadoresOrdenadosPorEstrellasQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()


  public fun ListarInvestigadoresOrdenadosPorEstrellasQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<ListarInvestigadoresOrdenadosPorEstrellasQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

