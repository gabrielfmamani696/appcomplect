
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


public interface ListarArchivosPublicosQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ListarArchivosPublicosQuery.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val archivos: List<ArchivosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class ArchivosItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val titulo: String,
    val tema: String,
    val descripcion: String,
    val fechaCreacion: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val imagenUrl: String?,
    val autorOriginal: String?,
    val licencia: String?,
    val nivelRequerido: NivelRequerido?,
    val espacio: Espacio?,
    val usuario: Usuario?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class NivelRequerido(
  
    val nombreRango: String,
    val jerarquia: Int?,
    val estrellasRequeridas: Int,
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Espacio(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreEspacio: String
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Usuario(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val alias: String,
    val rol: Rol
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Rol(
  
    val nombreRol: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ListarArchivosPublicos"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun ListarArchivosPublicosQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    ListarArchivosPublicosQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ListarArchivosPublicosQuery.execute(
  
  ): com.google.firebase.dataconnect.QueryResult<
    ListarArchivosPublicosQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()


  public fun ListarArchivosPublicosQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<ListarArchivosPublicosQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

