
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



public interface UnirseAEspacioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      UnirseAEspacioMutation.Data,
      UnirseAEspacioMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val espacioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val miembroEspacio_upsert: MiembroEspacioKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "UnirseAEspacio"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun UnirseAEspacioMutation.ref(
  
    usuarioId: java.util.UUID,espacioId: java.util.UUID,
  
  
): com.google.firebase.dataconnect.MutationRef<
    UnirseAEspacioMutation.Data,
    UnirseAEspacioMutation.Variables
  > =
  ref(
    
      UnirseAEspacioMutation.Variables(
        usuarioId=usuarioId,espacioId=espacioId,
  
      )
    
  )

public suspend fun UnirseAEspacioMutation.execute(
  
    usuarioId: java.util.UUID,espacioId: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    UnirseAEspacioMutation.Data,
    UnirseAEspacioMutation.Variables
  > =
  ref(
    
      usuarioId=usuarioId,espacioId=espacioId,
  
    
  ).execute()


