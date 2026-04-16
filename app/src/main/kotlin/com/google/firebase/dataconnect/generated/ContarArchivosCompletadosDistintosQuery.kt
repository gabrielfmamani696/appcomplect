
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


public interface ContarArchivosCompletadosDistintosQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ContarArchivosCompletadosDistintosQuery.Data,
      ContarArchivosCompletadosDistintosQuery.Variables
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
  
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?
  ) {
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ContarArchivosCompletadosDistintos"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ContarArchivosCompletadosDistintosQuery.ref(
  
    usuarioId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    ContarArchivosCompletadosDistintosQuery.Data,
    ContarArchivosCompletadosDistintosQuery.Variables
  > =
  ref(
    
      ContarArchivosCompletadosDistintosQuery.Variables(
        usuarioId=usuarioId,
  
      )
    
  )

public suspend fun ContarArchivosCompletadosDistintosQuery.execute(
  
    usuarioId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ContarArchivosCompletadosDistintosQuery.Data,
    ContarArchivosCompletadosDistintosQuery.Variables
  > =
  ref(
    
      usuarioId=usuarioId,
  
    
  ).execute()


  public fun ContarArchivosCompletadosDistintosQuery.flow(
    
      usuarioId: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<ContarArchivosCompletadosDistintosQuery.Data> =
    ref(
        
          usuarioId=usuarioId,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

