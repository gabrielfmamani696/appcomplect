
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



public interface EliminarCuestionariosDeArchivoMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      EliminarCuestionariosDeArchivoMutation.Data,
      EliminarCuestionariosDeArchivoMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val cuestionario_deleteMany: Int
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "EliminarCuestionariosDeArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun EliminarCuestionariosDeArchivoMutation.ref(
  
    archivoId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    EliminarCuestionariosDeArchivoMutation.Data,
    EliminarCuestionariosDeArchivoMutation.Variables
  > =
  ref(
    
      EliminarCuestionariosDeArchivoMutation.Variables(
        archivoId=archivoId,
  
      )
    
  )

public suspend fun EliminarCuestionariosDeArchivoMutation.execute(
  
    archivoId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    EliminarCuestionariosDeArchivoMutation.Data,
    EliminarCuestionariosDeArchivoMutation.Variables
  > =
  ref(
    
      archivoId=archivoId,
  
    
  ).execute()


