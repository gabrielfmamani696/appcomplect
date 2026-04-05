
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


public interface ObtenerPerfilCompletoQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerPerfilCompletoQuery.Data,
      ObtenerPerfilCompletoQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuario: Usuario?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Usuario(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val alias: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val estadoValidacion: Boolean,
    val estrellasPrestigio: Int,
    val rachaActualDias: Int,
    val numeroCelular: String,
    val curso: String?,
    val paralelo: String?,
    val nombreColegio: String?,
    val horaNotificacion: String?,
    val rol: Rol,
    val ultimaActividad: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val nivel: Nivel?,
    val avatar: Avatar?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Rol(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreRol: String
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Nivel(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreRango: String,
    val estrellasRequeridas: Int,
    val limitePalabrasTarjeta: Int
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class Avatar(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val imagenUrl: String,
    val descripcion: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerPerfilCompleto"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerPerfilCompletoQuery.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerPerfilCompletoQuery.Data,
    ObtenerPerfilCompletoQuery.Variables
  > =
  ref(
    
      ObtenerPerfilCompletoQuery.Variables(
        id=id,
  
      )
    
  )

public suspend fun ObtenerPerfilCompletoQuery.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerPerfilCompletoQuery.Data,
    ObtenerPerfilCompletoQuery.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


  public fun ObtenerPerfilCompletoQuery.flow(
    
      id: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerPerfilCompletoQuery.Data> =
    ref(
        
          id=id,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

