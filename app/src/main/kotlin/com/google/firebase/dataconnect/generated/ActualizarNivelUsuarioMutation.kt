
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



public interface ActualizarNivelUsuarioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      ActualizarNivelUsuarioMutation.Data,
      ActualizarNivelUsuarioMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nivelId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuario_update: UsuarioKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "ActualizarNivelUsuario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ActualizarNivelUsuarioMutation.ref(
  
    id: java.util.UUID,nivelId: java.util.UUID,

  
  
): com.google.firebase.dataconnect.MutationRef<
    ActualizarNivelUsuarioMutation.Data,
    ActualizarNivelUsuarioMutation.Variables
  > =
  ref(
    
      ActualizarNivelUsuarioMutation.Variables(
        id=id,nivelId=nivelId,
  
      )
    
  )

public suspend fun ActualizarNivelUsuarioMutation.execute(

  
    
      id: java.util.UUID,nivelId: java.util.UUID,

  

  ): com.google.firebase.dataconnect.MutationResult<
    ActualizarNivelUsuarioMutation.Data,
    ActualizarNivelUsuarioMutation.Variables
  > =
  ref(
    
      id=id,nivelId=nivelId,
  
    
  ).execute()


