
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



public interface ActualizarEstrellasUsuarioMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      ActualizarEstrellasUsuarioMutation.Data,
      ActualizarEstrellasUsuarioMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nuevasEstrellas: Int
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuario_update: UsuarioKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "ActualizarEstrellasUsuario"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ActualizarEstrellasUsuarioMutation.ref(
  
    id: java.util.UUID,nuevasEstrellas: Int,
  
  
): com.google.firebase.dataconnect.MutationRef<
    ActualizarEstrellasUsuarioMutation.Data,
    ActualizarEstrellasUsuarioMutation.Variables
  > =
  ref(
    
      ActualizarEstrellasUsuarioMutation.Variables(
        id=id,nuevasEstrellas=nuevasEstrellas,
  
      )
    
  )

public suspend fun ActualizarEstrellasUsuarioMutation.execute(
  
    id: java.util.UUID,nuevasEstrellas: Int,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    ActualizarEstrellasUsuarioMutation.Data,
    ActualizarEstrellasUsuarioMutation.Variables
  > =
  ref(
    
      id=id,nuevasEstrellas=nuevasEstrellas,
  
    
  ).execute()


