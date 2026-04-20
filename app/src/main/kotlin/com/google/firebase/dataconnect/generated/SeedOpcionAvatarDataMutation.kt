
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



public interface SeedOpcionAvatarDataMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      SeedOpcionAvatarDataMutation.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val opcionAvatar_insertMany: List<OpcionAvatarKey>
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "SeedOpcionAvatarData"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun SeedOpcionAvatarDataMutation.ref(
  
): com.google.firebase.dataconnect.MutationRef<
    SeedOpcionAvatarDataMutation.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun SeedOpcionAvatarDataMutation.execute(

  

  ): com.google.firebase.dataconnect.MutationResult<
    SeedOpcionAvatarDataMutation.Data,
    Unit
  > =
  ref(
    
  ).execute()


