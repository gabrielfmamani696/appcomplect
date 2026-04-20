
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


public interface ListarInsigniasObtenidasUsuarioQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ListarInsigniasObtenidasUsuarioQuery.Data,
      ListarInsigniasObtenidasUsuarioQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val logroNotificados: List<LogroNotificadosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class LogroNotificadosItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fechaNotificacion: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val insignia: Insignia?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Insignia(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreVisible: String,
    val descripcion: String,
    val iconoRef: String,
    val condicionDesbloqueo: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListarInsigniasObtenidasUsuario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ListarInsigniasObtenidasUsuarioQuery.ref(
  
    usuarioId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.QueryRef<
    ListarInsigniasObtenidasUsuarioQuery.Data,
    ListarInsigniasObtenidasUsuarioQuery.Variables
  > =
  ref(
    
      ListarInsigniasObtenidasUsuarioQuery.Variables(
        usuarioId=usuarioId,
  
      )
    
  )

public suspend fun ListarInsigniasObtenidasUsuarioQuery.execute(

  
    
      usuarioId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.QueryResult<
    ListarInsigniasObtenidasUsuarioQuery.Data,
    ListarInsigniasObtenidasUsuarioQuery.Variables
  > =
  ref(
    
      usuarioId=usuarioId,
  
    
  ).execute()


  public fun ListarInsigniasObtenidasUsuarioQuery.flow(
    
      usuarioId: java.util.UUID,

  
    
    ): kotlinx.coroutines.flow.Flow<ListarInsigniasObtenidasUsuarioQuery.Data> =
    ref(
        
          usuarioId=usuarioId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

