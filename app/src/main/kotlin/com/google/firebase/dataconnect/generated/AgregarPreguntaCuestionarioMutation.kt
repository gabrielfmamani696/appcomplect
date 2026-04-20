
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



public interface AgregarPreguntaCuestionarioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      AgregarPreguntaCuestionarioMutation.Data,
      AgregarPreguntaCuestionarioMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val cuestionarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val enunciado: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val pregunta_insert: PreguntaKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "AgregarPreguntaCuestionario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun AgregarPreguntaCuestionarioMutation.ref(
  
    cuestionarioId: java.util.UUID,enunciado: String,

  
  
): com.google.firebase.dataconnect.MutationRef<
    AgregarPreguntaCuestionarioMutation.Data,
    AgregarPreguntaCuestionarioMutation.Variables
  > =
  ref(
    
      AgregarPreguntaCuestionarioMutation.Variables(
        cuestionarioId=cuestionarioId,enunciado=enunciado,
  
      )
    
  )

public suspend fun AgregarPreguntaCuestionarioMutation.execute(

  
    
      cuestionarioId: java.util.UUID,enunciado: String,

  

  ): com.google.firebase.dataconnect.MutationResult<
    AgregarPreguntaCuestionarioMutation.Data,
    AgregarPreguntaCuestionarioMutation.Variables
  > =
  ref(
    
      cuestionarioId=cuestionarioId,enunciado=enunciado,
  
    
  ).execute()


