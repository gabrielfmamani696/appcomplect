
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


public interface ObtenerEstadisticasArchivoQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerEstadisticasArchivoQuery.Data,
      ObtenerEstadisticasArchivoQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val intentos: List<IntentosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class IntentosItem(
  
    val calificacionObtenida: Int,
    val usuario: Usuario?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Usuario(
  
    val nombre: String,
    val apellidoPaterno: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerEstadisticasArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerEstadisticasArchivoQuery.ref(
  
    archivoId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerEstadisticasArchivoQuery.Data,
    ObtenerEstadisticasArchivoQuery.Variables
  > =
  ref(
    
      ObtenerEstadisticasArchivoQuery.Variables(
        archivoId=archivoId,
  
      )
    
  )

public suspend fun ObtenerEstadisticasArchivoQuery.execute(

  
    
      archivoId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerEstadisticasArchivoQuery.Data,
    ObtenerEstadisticasArchivoQuery.Variables
  > =
  ref(
    
      archivoId=archivoId,
  
    
  ).execute()


  public fun ObtenerEstadisticasArchivoQuery.flow(
    
      archivoId: java.util.UUID,

  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerEstadisticasArchivoQuery.Data> =
    ref(
        
          archivoId=archivoId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

