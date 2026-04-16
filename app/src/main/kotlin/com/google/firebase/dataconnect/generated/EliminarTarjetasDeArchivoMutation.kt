
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



public interface EliminarTarjetasDeArchivoMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      EliminarTarjetasDeArchivoMutation.Data,
      EliminarTarjetasDeArchivoMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val tarjeta_deleteMany: Int
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "EliminarTarjetasDeArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun EliminarTarjetasDeArchivoMutation.ref(
  
    archivoId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    EliminarTarjetasDeArchivoMutation.Data,
    EliminarTarjetasDeArchivoMutation.Variables
  > =
  ref(
    
      EliminarTarjetasDeArchivoMutation.Variables(
        archivoId=archivoId,
  
      )
    
  )

public suspend fun EliminarTarjetasDeArchivoMutation.execute(
  
    archivoId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    EliminarTarjetasDeArchivoMutation.Data,
    EliminarTarjetasDeArchivoMutation.Variables
  > =
  ref(
    
      archivoId=archivoId,
  
    
  ).execute()


