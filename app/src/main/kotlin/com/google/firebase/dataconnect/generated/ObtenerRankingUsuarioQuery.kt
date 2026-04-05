
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


public interface ObtenerRankingUsuarioQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerRankingUsuarioQuery.Data,
      ObtenerRankingUsuarioQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val misEstrellas: Int
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuarios: List<UsuariosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class UsuariosItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerRankingUsuario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerRankingUsuarioQuery.ref(
  
    misEstrellas: Int,
  
  
): com.google.firebase.dataconnect.QueryRef<
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
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerRankingUsuarioQuery.Data,
    ObtenerRankingUsuarioQuery.Variables
  > =
  ref(
    
      misEstrellas=misEstrellas,
  
    
  ).execute()


  public fun ObtenerRankingUsuarioQuery.flow(
    
      misEstrellas: Int,
  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerRankingUsuarioQuery.Data> =
    ref(
        
          misEstrellas=misEstrellas,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

