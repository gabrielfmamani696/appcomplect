
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



public interface ActualizarUsuarioPerfilMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      ActualizarUsuarioPerfilMutation.Data,
      ActualizarUsuarioPerfilMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val alias: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val numeroCelular: String,
    val curso: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val paralelo: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val nombreColegio: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val rolId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val avatarId: com.google.firebase.dataconnect.OptionalVariable<@kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?>,
    val horaNotificacion: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var alias: String
        public var nombre: String
        public var apellidoPaterno: String
        public var apellidoMaterno: String
        public var numeroCelular: String
        public var curso: String?
        public var paralelo: String?
        public var nombreColegio: String?
        public var rolId: java.util.UUID
        public var avatarId: java.util.UUID?
        public var horaNotificacion: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,numeroCelular: String,rolId: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var alias= alias
            var nombre= nombre
            var apellidoPaterno= apellidoPaterno
            var apellidoMaterno= apellidoMaterno
            var numeroCelular= numeroCelular
            var curso: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var paralelo: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var nombreColegio: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var rolId= rolId
            var avatarId: com.google.firebase.dataconnect.OptionalVariable<java.util.UUID?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var horaNotificacion: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
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
              set(value_) { curso = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var paralelo: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { paralelo = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var nombreColegio: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nombreColegio = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var rolId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { rolId = value_ }
              
            override var avatarId: java.util.UUID?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { avatarId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var horaNotificacion: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { horaNotificacion = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,numeroCelular=numeroCelular,curso=curso,paralelo=paralelo,nombreColegio=nombreColegio,rolId=rolId,avatarId=avatarId,horaNotificacion=horaNotificacion,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuario_update: UsuarioKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "ActualizarUsuarioPerfil"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ActualizarUsuarioPerfilMutation.ref(
  
    id: java.util.UUID,alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,numeroCelular: String,rolId: java.util.UUID,
  
    block_: ActualizarUsuarioPerfilMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
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
  
    id: java.util.UUID,alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,numeroCelular: String,rolId: java.util.UUID,
  
    block_: ActualizarUsuarioPerfilMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    ActualizarUsuarioPerfilMutation.Data,
    ActualizarUsuarioPerfilMutation.Variables
  > =
  ref(
    
      id=id,alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,numeroCelular=numeroCelular,rolId=rolId,
  
    block_
    
  ).execute()


