
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



public interface EliminarRespuestasDePreguntaMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      EliminarRespuestasDePreguntaMutation.Data,
      EliminarRespuestasDePreguntaMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val preguntaId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val respuesta_deleteMany: Int
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "EliminarRespuestasDePregunta"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun EliminarRespuestasDePreguntaMutation.ref(
  
    preguntaId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    EliminarRespuestasDePreguntaMutation.Data,
    EliminarRespuestasDePreguntaMutation.Variables
  > =
  ref(
    
      EliminarRespuestasDePreguntaMutation.Variables(
        preguntaId=preguntaId,
  
      )
    
  )

public suspend fun EliminarRespuestasDePreguntaMutation.execute(
  
    preguntaId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    EliminarRespuestasDePreguntaMutation.Data,
    EliminarRespuestasDePreguntaMutation.Variables
  > =
  ref(
    
      preguntaId=preguntaId,
  
    
  ).execute()


