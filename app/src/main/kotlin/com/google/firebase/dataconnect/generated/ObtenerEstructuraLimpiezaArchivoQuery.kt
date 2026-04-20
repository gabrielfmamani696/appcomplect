
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


public interface ObtenerEstructuraLimpiezaArchivoQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerEstructuraLimpiezaArchivoQuery.Data,
      ObtenerEstructuraLimpiezaArchivoQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val archivo: Archivo?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Archivo(
  
    val cuestionarios_on_archivo: List<CuestionariosOnArchivoItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class CuestionariosOnArchivoItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val preguntas_on_cuestionario: List<PreguntasOnCuestionarioItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class PreguntasOnCuestionarioItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerEstructuraLimpiezaArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerEstructuraLimpiezaArchivoQuery.ref(
  
    archivoId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerEstructuraLimpiezaArchivoQuery.Data,
    ObtenerEstructuraLimpiezaArchivoQuery.Variables
  > =
  ref(
    
      ObtenerEstructuraLimpiezaArchivoQuery.Variables(
        archivoId=archivoId,
  
      )
    
  )

public suspend fun ObtenerEstructuraLimpiezaArchivoQuery.execute(

  
    
      archivoId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerEstructuraLimpiezaArchivoQuery.Data,
    ObtenerEstructuraLimpiezaArchivoQuery.Variables
  > =
  ref(
    
      archivoId=archivoId,
  
    
  ).execute()


  public fun ObtenerEstructuraLimpiezaArchivoQuery.flow(
    
      archivoId: java.util.UUID,

  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerEstructuraLimpiezaArchivoQuery.Data> =
    ref(
        
          archivoId=archivoId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

