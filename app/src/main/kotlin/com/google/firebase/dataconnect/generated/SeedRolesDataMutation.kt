
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



public interface SeedRolesDataMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      SeedRolesDataMutation.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val rolUsuario_insertMany: List<RolUsuarioKey>
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "SeedRolesData"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun SeedRolesDataMutation.ref(
  
): com.google.firebase.dataconnect.MutationRef<
    SeedRolesDataMutation.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun SeedRolesDataMutation.execute(

  

  ): com.google.firebase.dataconnect.MutationResult<
    SeedRolesDataMutation.Data,
    Unit
  > =
  ref(
    
  ).execute()


