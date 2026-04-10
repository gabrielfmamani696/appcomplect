
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



public interface AgregarRespuestaPreguntaMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      AgregarRespuestaPreguntaMutation.Data,
      AgregarRespuestaPreguntaMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val preguntaId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val textoOpcion: String,
    val esCorrecta: Boolean
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val respuesta_insert: RespuestaKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "AgregarRespuestaPregunta"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun AgregarRespuestaPreguntaMutation.ref(
  
    preguntaId: java.util.UUID,textoOpcion: String,esCorrecta: Boolean,
  
  
): com.google.firebase.dataconnect.MutationRef<
    AgregarRespuestaPreguntaMutation.Data,
    AgregarRespuestaPreguntaMutation.Variables
  > =
  ref(
    
      AgregarRespuestaPreguntaMutation.Variables(
        preguntaId=preguntaId,textoOpcion=textoOpcion,esCorrecta=esCorrecta,
  
      )
    
  )

public suspend fun AgregarRespuestaPreguntaMutation.execute(
  
    preguntaId: java.util.UUID,textoOpcion: String,esCorrecta: Boolean,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    AgregarRespuestaPreguntaMutation.Data,
    AgregarRespuestaPreguntaMutation.Variables
  > =
  ref(
    
      preguntaId=preguntaId,textoOpcion=textoOpcion,esCorrecta=esCorrecta,
  
    
  ).execute()


