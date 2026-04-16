
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



public interface EliminarArchivoMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      EliminarArchivoMutation.Data,
      EliminarArchivoMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val archivo_delete: ArchivoKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "EliminarArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun EliminarArchivoMutation.ref(
  
    id: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    EliminarArchivoMutation.Data,
    EliminarArchivoMutation.Variables
  > =
  ref(
    
      EliminarArchivoMutation.Variables(
        id=id,
  
      )
    
  )

public suspend fun EliminarArchivoMutation.execute(
  
    id: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    EliminarArchivoMutation.Data,
    EliminarArchivoMutation.Variables
  > =
  ref(
    
      id=id,
  
    
  ).execute()


