
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


public interface BuscarEspacioPorCodigoQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      BuscarEspacioPorCodigoQuery.Data,
      BuscarEspacioPorCodigoQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val codigoAcceso: String
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
    public val operationName: String = "BuscarEspacioPorCodigo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun BuscarEspacioPorCodigoQuery.ref(
  
    codigoAcceso: String,
  
  
): com.google.firebase.dataconnect.QueryRef<
    BuscarEspacioPorCodigoQuery.Data,
    BuscarEspacioPorCodigoQuery.Variables
  > =
  ref(
    
      BuscarEspacioPorCodigoQuery.Variables(
        codigoAcceso=codigoAcceso,
  
      )
    
  )

public suspend fun BuscarEspacioPorCodigoQuery.execute(
  
    codigoAcceso: String,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    BuscarEspacioPorCodigoQuery.Data,
    BuscarEspacioPorCodigoQuery.Variables
  > =
  ref(
    
      codigoAcceso=codigoAcceso,
  
    
  ).execute()


  public fun BuscarEspacioPorCodigoQuery.flow(
    
      codigoAcceso: String,
  
    
    ): kotlinx.coroutines.flow.Flow<BuscarEspacioPorCodigoQuery.Data> =
    ref(
        
          codigoAcceso=codigoAcceso,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

