
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



public interface RegistrarIntentoExamenMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      RegistrarIntentoExamenMutation.Data,
      RegistrarIntentoExamenMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val calificacionObtenida: Int,
    val fechaIntento: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val completadoExitosamente: Boolean
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val intento_insert: IntentoKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "RegistrarIntentoExamen"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun RegistrarIntentoExamenMutation.ref(
  
    usuarioId: java.util.UUID,archivoId: java.util.UUID,calificacionObtenida: Int,fechaIntento: com.google.firebase.Timestamp,completadoExitosamente: Boolean,
  
  
): com.google.firebase.dataconnect.MutationRef<
    RegistrarIntentoExamenMutation.Data,
    RegistrarIntentoExamenMutation.Variables
  > =
  ref(
    
      RegistrarIntentoExamenMutation.Variables(
        usuarioId=usuarioId,archivoId=archivoId,calificacionObtenida=calificacionObtenida,fechaIntento=fechaIntento,completadoExitosamente=completadoExitosamente,
  
      )
    
  )

public suspend fun RegistrarIntentoExamenMutation.execute(
  
    usuarioId: java.util.UUID,archivoId: java.util.UUID,calificacionObtenida: Int,fechaIntento: com.google.firebase.Timestamp,completadoExitosamente: Boolean,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    RegistrarIntentoExamenMutation.Data,
    RegistrarIntentoExamenMutation.Variables
  > =
  ref(
    
      usuarioId=usuarioId,archivoId=archivoId,calificacionObtenida=calificacionObtenida,fechaIntento=fechaIntento,completadoExitosamente=completadoExitosamente,
  
    
  ).execute()


