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

import com.google.firebase.dataconnect.serializers.UUIDSerializer
import java.util.UUID
import kotlinx.serialization.Serializable

@Serializable
public data class OpcionAvatarKey(
  val id: @Serializable(with = UUIDSerializer::class) UUID
)

@Serializable
public data class RolUsuarioKey(
  val id: @Serializable(with = UUIDSerializer::class) UUID
)

@Serializable
public data class UsuarioKey(
  val id: @Serializable(with = UUIDSerializer::class) UUID
)
