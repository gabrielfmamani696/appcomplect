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
import com.google.firebase.dataconnect.OptionalVariable
import com.google.firebase.dataconnect.serializers.UUIDSerializer
import java.util.UUID
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer

public interface ActualizarUsuarioPerfilMutation :
    GeneratedMutation<
      DefaultConnector,
      ActualizarUsuarioPerfilMutation.Data,
      ActualizarUsuarioPerfilMutation.Variables
    >
{

    @Serializable
  public data class Variables(

    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val alias: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val numeroCelular: String,
    val curso: OptionalVariable<String?>,
    val paralelo: OptionalVariable<String?>,
    val nombreColegio: OptionalVariable<String?>,
    val rolId: @Serializable(with = UUIDSerializer::class) UUID,
    val avatarId: OptionalVariable<@Serializable(with = UUIDSerializer::class) UUID?>,
    val horaNotificacion: OptionalVariable<String?>
  ) {


      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: UUID
        public var alias: String
        public var nombre: String
        public var apellidoPaterno: String
        public var apellidoMaterno: String
        public var numeroCelular: String
        public var curso: String?
        public var paralelo: String?
        public var nombreColegio: String?
        public var rolId: UUID
        public var avatarId: UUID?
        public var horaNotificacion: String?

      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: UUID,alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,numeroCelular: String,rolId: UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var alias= alias
            var nombre= nombre
            var apellidoPaterno= apellidoPaterno
            var apellidoMaterno= apellidoMaterno
            var numeroCelular= numeroCelular
            var curso: OptionalVariable<String?> =
                OptionalVariable.Undefined
            var paralelo: OptionalVariable<String?> =
                OptionalVariable.Undefined
            var nombreColegio: OptionalVariable<String?> =
                OptionalVariable.Undefined
            var rolId= rolId
            var avatarId: OptionalVariable<UUID?> =
                OptionalVariable.Undefined
            var horaNotificacion: OptionalVariable<String?> =
                OptionalVariable.Undefined


          return object : Builder {
            override var id: UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }

            override var alias: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { alias = value_ }

            override var nombre: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nombre = value_ }

            override var apellidoPaterno: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { apellidoPaterno = value_ }

            override var apellidoMaterno: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { apellidoMaterno = value_ }

            override var numeroCelular: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { numeroCelular = value_ }

            override var curso: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { curso = OptionalVariable.Value(value_) }

            override var paralelo: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { paralelo = OptionalVariable.Value(value_) }

            override var nombreColegio: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nombreColegio = OptionalVariable.Value(value_) }

            override var rolId: UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { rolId = value_ }

            override var avatarId: UUID?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { avatarId = OptionalVariable.Value(value_) }

            override var horaNotificacion: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { horaNotificacion = OptionalVariable.Value(value_) }


          }.apply(block_)
          .let {
            Variables(
              id=id,alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,numeroCelular=numeroCelular,curso=curso,paralelo=paralelo,nombreColegio=nombreColegio,rolId=rolId,avatarId=avatarId,horaNotificacion=horaNotificacion,
            )
          }
        }
      }

  }



    @Serializable
  public data class Data(

    val usuario_update: UsuarioKey?
  ) {


  }


  public companion object {
    public val operationName: String = "ActualizarUsuarioPerfil"

    public val dataDeserializer: DeserializationStrategy<Data> =
      serializer()

    public val variablesSerializer: SerializationStrategy<Variables> =
      serializer()
  }
}

public fun ActualizarUsuarioPerfilMutation.ref(

    id: UUID,alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,numeroCelular: String,rolId: UUID,

    block_: ActualizarUsuarioPerfilMutation.Variables.Builder.() -> Unit = {}

): MutationRef<
    ActualizarUsuarioPerfilMutation.Data,
    ActualizarUsuarioPerfilMutation.Variables
  > =
  ref(

      ActualizarUsuarioPerfilMutation.Variables.build(
        id=id,alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,numeroCelular=numeroCelular,rolId=rolId,

    block_
      )

  )

public suspend fun ActualizarUsuarioPerfilMutation.execute(

    id: UUID,alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,numeroCelular: String,rolId: UUID,

    block_: ActualizarUsuarioPerfilMutation.Variables.Builder.() -> Unit = {}

  ): MutationResult<
    ActualizarUsuarioPerfilMutation.Data,
    ActualizarUsuarioPerfilMutation.Variables
  > =
  ref(

      id=id,alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,numeroCelular=numeroCelular,rolId=rolId,

    block_

  ).execute()
