
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


public interface ObtenerArchivoParaEdicionQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerArchivoParaEdicionQuery.Data,
      ObtenerArchivoParaEdicionQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val archivo: Archivo?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Archivo(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val titulo: String,
    val tema: String,
    val descripcion: String,
    val imagenUrl: String?,
    val autorOriginal: String?,
    val licencia: String?,
    val nivelRequerido: NivelRequerido?,
    val usuario: Usuario?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class NivelRequerido(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Usuario(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerArchivoParaEdicion"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerArchivoParaEdicionQuery.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerArchivoParaEdicionQuery.Data,
    ObtenerArchivoParaEdicionQuery.Variables
  > =
  ref(
    
      ObtenerArchivoParaEdicionQuery.Variables(
        id=id,
  
      )
    
  )

public suspend fun ObtenerArchivoParaEdicionQuery.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerArchivoParaEdicionQuery.Data,
    ObtenerArchivoParaEdicionQuery.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


  public fun ObtenerArchivoParaEdicionQuery.flow(
    
      id: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerArchivoParaEdicionQuery.Data> =
    ref(
        
          id=id,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

