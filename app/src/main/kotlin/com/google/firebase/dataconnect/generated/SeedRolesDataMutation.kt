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

import com.google.firebase.dataconnect.MutationRef
import com.google.firebase.dataconnect.MutationResult
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer

public interface SeedRolesDataMutation :
    GeneratedMutation<
      DefaultConnector,
      SeedRolesDataMutation.Data,
      Unit
    >
{
  
    @Serializable
  public data class Data(
    val rolUsuario_insertMany: List<RolUsuarioKey>
  )

  public companion object {
    public val operationName: String = "SeedRolesData"

    public val dataDeserializer: DeserializationStrategy<Data> =
      serializer()

    public val variablesSerializer: SerializationStrategy<Unit> =
      serializer()
  }
}

public fun SeedRolesDataMutation.ref(
): MutationRef<
    SeedRolesDataMutation.Data,
    Unit
  > =
  ref(
      Unit
  )

public suspend fun SeedRolesDataMutation.execute(
  ): MutationResult<
    SeedRolesDataMutation.Data,
    Unit
  > =
  ref(
  ).execute()
