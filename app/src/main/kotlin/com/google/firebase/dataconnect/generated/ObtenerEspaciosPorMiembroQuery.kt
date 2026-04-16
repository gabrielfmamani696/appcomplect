
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


public interface ObtenerEspaciosPorMiembroQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerEspaciosPorMiembroQuery.Data,
      ObtenerEspaciosPorMiembroQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val miembroEspacios: List<MiembroEspaciosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class MiembroEspaciosItem(
  
    val espacio: Espacio
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Espacio(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreEspacio: String,
    val codigoAcceso: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerEspaciosPorMiembro"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerEspaciosPorMiembroQuery.ref(
  
    usuarioId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerEspaciosPorMiembroQuery.Data,
    ObtenerEspaciosPorMiembroQuery.Variables
  > =
  ref(
    
      ObtenerEspaciosPorMiembroQuery.Variables(
        usuarioId=usuarioId,
  
      )
    
  )

public suspend fun ObtenerEspaciosPorMiembroQuery.execute(
  
    usuarioId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerEspaciosPorMiembroQuery.Data,
    ObtenerEspaciosPorMiembroQuery.Variables
  > =
  ref(
    
      usuarioId=usuarioId,
  
    
  ).execute()


  public fun ObtenerEspaciosPorMiembroQuery.flow(
    
      usuarioId: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerEspaciosPorMiembroQuery.Data> =
    ref(
        
          usuarioId=usuarioId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

