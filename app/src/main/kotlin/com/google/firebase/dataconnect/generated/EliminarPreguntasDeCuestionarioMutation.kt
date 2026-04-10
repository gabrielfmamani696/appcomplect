
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



public interface EliminarPreguntasDeCuestionarioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      EliminarPreguntasDeCuestionarioMutation.Data,
      EliminarPreguntasDeCuestionarioMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val cuestionarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val pregunta_deleteMany: Int
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "EliminarPreguntasDeCuestionario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun EliminarPreguntasDeCuestionarioMutation.ref(
  
    cuestionarioId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    EliminarPreguntasDeCuestionarioMutation.Data,
    EliminarPreguntasDeCuestionarioMutation.Variables
  > =
  ref(
    
      EliminarPreguntasDeCuestionarioMutation.Variables(
        cuestionarioId=cuestionarioId,
  
      )
    
  )

public suspend fun EliminarPreguntasDeCuestionarioMutation.execute(
  
    cuestionarioId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    EliminarPreguntasDeCuestionarioMutation.Data,
    EliminarPreguntasDeCuestionarioMutation.Variables
  > =
  ref(
    
      cuestionarioId=cuestionarioId,
  
    
  ).execute()


