
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



public interface CrearSolicitudValidacionMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      CrearSolicitudValidacionMutation.Data,
      CrearSolicitudValidacionMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val rolSolicitadoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val fechaEnvio: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val solicitudValidacion_insert: SolicitudValidacionKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CrearSolicitudValidacion"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CrearSolicitudValidacionMutation.ref(
  
    usuarioId: java.util.UUID,rolSolicitadoId: java.util.UUID,fechaEnvio: com.google.firebase.Timestamp,

  
  
): com.google.firebase.dataconnect.MutationRef<
    CrearSolicitudValidacionMutation.Data,
    CrearSolicitudValidacionMutation.Variables
  > =
  ref(
    
      CrearSolicitudValidacionMutation.Variables(
        usuarioId=usuarioId,rolSolicitadoId=rolSolicitadoId,fechaEnvio=fechaEnvio,
  
      )
    
  )

public suspend fun CrearSolicitudValidacionMutation.execute(

  
    
      usuarioId: java.util.UUID,rolSolicitadoId: java.util.UUID,fechaEnvio: com.google.firebase.Timestamp,

  

  ): com.google.firebase.dataconnect.MutationResult<
    CrearSolicitudValidacionMutation.Data,
    CrearSolicitudValidacionMutation.Variables
  > =
  ref(
    
      usuarioId=usuarioId,rolSolicitadoId=rolSolicitadoId,fechaEnvio=fechaEnvio,
  
    
  ).execute()


