
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



public interface AgregarCuestionarioArchivoMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      AgregarCuestionarioArchivoMutation.Data,
      AgregarCuestionarioArchivoMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val tituloQuiz: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val cuestionario_insert: CuestionarioKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "AgregarCuestionarioArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun AgregarCuestionarioArchivoMutation.ref(
  
    archivoId: java.util.UUID,tituloQuiz: String,
  
  
): com.google.firebase.dataconnect.MutationRef<
    AgregarCuestionarioArchivoMutation.Data,
    AgregarCuestionarioArchivoMutation.Variables
  > =
  ref(
    
      AgregarCuestionarioArchivoMutation.Variables(
        archivoId=archivoId,tituloQuiz=tituloQuiz,
  
      )
    
  )

public suspend fun AgregarCuestionarioArchivoMutation.execute(
  
    archivoId: java.util.UUID,tituloQuiz: String,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    AgregarCuestionarioArchivoMutation.Data,
    AgregarCuestionarioArchivoMutation.Variables
  > =
  ref(
    
      archivoId=archivoId,tituloQuiz=tituloQuiz,
  
    
  ).execute()


