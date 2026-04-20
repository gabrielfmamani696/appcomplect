
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



public interface RegistrarLogroNotificadoMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      RegistrarLogroNotificadoMutation.Data,
      RegistrarLogroNotificadoMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val insigniaId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val logroNotificado_insert: LogroNotificadoKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "RegistrarLogroNotificado"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun RegistrarLogroNotificadoMutation.ref(
  
    usuarioId: java.util.UUID,insigniaId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.MutationRef<
    RegistrarLogroNotificadoMutation.Data,
    RegistrarLogroNotificadoMutation.Variables
  > =
  ref(
    
      RegistrarLogroNotificadoMutation.Variables(
        usuarioId=usuarioId,insigniaId=insigniaId,
  
      )
    
  )

public suspend fun RegistrarLogroNotificadoMutation.execute(

  
    
      usuarioId: java.util.UUID,insigniaId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.MutationResult<
    RegistrarLogroNotificadoMutation.Data,
    RegistrarLogroNotificadoMutation.Variables
  > =
  ref(
    
      usuarioId=usuarioId,insigniaId=insigniaId,
  
    
  ).execute()


