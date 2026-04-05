
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



public interface ActualizarRachaUsuarioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      ActualizarRachaUsuarioMutation.Data,
      ActualizarRachaUsuarioMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val racha: Int,
    val ultimaActividad: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuario_update: UsuarioKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "ActualizarRachaUsuario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ActualizarRachaUsuarioMutation.ref(
  
    id: java.util.UUID,racha: Int,ultimaActividad: com.google.firebase.Timestamp,
  
  
): com.google.firebase.dataconnect.MutationRef<
    ActualizarRachaUsuarioMutation.Data,
    ActualizarRachaUsuarioMutation.Variables
  > =
  ref(
    
      ActualizarRachaUsuarioMutation.Variables(
        id=id,racha=racha,ultimaActividad=ultimaActividad,
  
      )
    
  )

public suspend fun ActualizarRachaUsuarioMutation.execute(
  
    id: java.util.UUID,racha: Int,ultimaActividad: com.google.firebase.Timestamp,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    ActualizarRachaUsuarioMutation.Data,
    ActualizarRachaUsuarioMutation.Variables
  > =
  ref(
    
      id=id,racha=racha,ultimaActividad=ultimaActividad,
  
    
  ).execute()


