
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


public interface ObtenerMisEspaciosQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerMisEspaciosQuery.Data,
      ObtenerMisEspaciosQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val espacioAprendizajes: List<EspacioAprendizajesItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class EspacioAprendizajesItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreEspacio: String,
    val codigoAcceso: String
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerMisEspacios"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerMisEspaciosQuery.ref(
  
    usuarioId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerMisEspaciosQuery.Data,
    ObtenerMisEspaciosQuery.Variables
  > =
  ref(
    
      ObtenerMisEspaciosQuery.Variables(
        usuarioId=usuarioId,
  
      )
    
  )

public suspend fun ObtenerMisEspaciosQuery.execute(

  
    
      usuarioId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerMisEspaciosQuery.Data,
    ObtenerMisEspaciosQuery.Variables
  > =
  ref(
    
      usuarioId=usuarioId,
  
    
  ).execute()


  public fun ObtenerMisEspaciosQuery.flow(
    
      usuarioId: java.util.UUID,

  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerMisEspaciosQuery.Data> =
    ref(
        
          usuarioId=usuarioId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

