
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


public interface ContarIntentosUsuarioQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ContarIntentosUsuarioQuery.Data,
      ContarIntentosUsuarioQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val intentos: List<IntentosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class IntentosItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?,
    val completadoExitosamente: Boolean
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ContarIntentosUsuario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ContarIntentosUsuarioQuery.ref(
  
    usuarioId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    ContarIntentosUsuarioQuery.Data,
    ContarIntentosUsuarioQuery.Variables
  > =
  ref(
    
      ContarIntentosUsuarioQuery.Variables(
        usuarioId=usuarioId,
  
      )
    
  )

public suspend fun ContarIntentosUsuarioQuery.execute(
  
    usuarioId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ContarIntentosUsuarioQuery.Data,
    ContarIntentosUsuarioQuery.Variables
  > =
  ref(
    
      usuarioId=usuarioId,
  
    
  ).execute()


  public fun ContarIntentosUsuarioQuery.flow(
    
      usuarioId: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<ContarIntentosUsuarioQuery.Data> =
    ref(
        
          usuarioId=usuarioId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

