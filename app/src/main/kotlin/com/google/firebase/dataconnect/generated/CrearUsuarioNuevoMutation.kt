
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



public interface CrearUsuarioNuevoMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      CrearUsuarioNuevoMutation.Data,
      CrearUsuarioNuevoMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val alias: String,
    val nombre: String,
    val apellidoPaterno: String,
    val apellidoMaterno: String,
    val estadoValidacion: Boolean,
    val estrellasPrestigio: Int,
    val rachaActualDias: Int,
    val tipoUsuario: Int,
    val ultimaActividad: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val avatarId: com.google.firebase.dataconnect.OptionalVariable<@kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?>,
    val nivelId: com.google.firebase.dataconnect.OptionalVariable<@kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var alias: String
        public var nombre: String
        public var apellidoPaterno: String
        public var apellidoMaterno: String
        public var estadoValidacion: Boolean
        public var estrellasPrestigio: Int
        public var rachaActualDias: Int
        public var tipoUsuario: Int
        public var ultimaActividad: com.google.firebase.Timestamp
        public var avatarId: java.util.UUID?
        public var nivelId: java.util.UUID?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,estadoValidacion: Boolean,estrellasPrestigio: Int,rachaActualDias: Int,tipoUsuario: Int,ultimaActividad: com.google.firebase.Timestamp,
          block_: Builder.() -> Unit
        ): Variables {
          var alias= alias
            var nombre= nombre
            var apellidoPaterno= apellidoPaterno
            var apellidoMaterno= apellidoMaterno
            var estadoValidacion= estadoValidacion
            var estrellasPrestigio= estrellasPrestigio
            var rachaActualDias= rachaActualDias
            var tipoUsuario= tipoUsuario
            var ultimaActividad= ultimaActividad
            var avatarId: com.google.firebase.dataconnect.OptionalVariable<java.util.UUID?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var nivelId: com.google.firebase.dataconnect.OptionalVariable<java.util.UUID?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
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
              
            override var estadoValidacion: Boolean
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { estadoValidacion = value_ }
              
            override var estrellasPrestigio: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { estrellasPrestigio = value_ }
              
            override var rachaActualDias: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { rachaActualDias = value_ }
              
            override var tipoUsuario: Int
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { tipoUsuario = value_ }
              
            override var ultimaActividad: com.google.firebase.Timestamp
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { ultimaActividad = value_ }
              
            override var avatarId: java.util.UUID?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { avatarId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var nivelId: java.util.UUID?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nivelId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,estadoValidacion=estadoValidacion,estrellasPrestigio=estrellasPrestigio,rachaActualDias=rachaActualDias,tipoUsuario=tipoUsuario,ultimaActividad=ultimaActividad,avatarId=avatarId,nivelId=nivelId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuario_insert: UsuarioKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CrearUsuarioNuevo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CrearUsuarioNuevoMutation.ref(
  
    alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,estadoValidacion: Boolean,estrellasPrestigio: Int,rachaActualDias: Int,tipoUsuario: Int,ultimaActividad: com.google.firebase.Timestamp,
  
    block_: CrearUsuarioNuevoMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CrearUsuarioNuevoMutation.Data,
    CrearUsuarioNuevoMutation.Variables
  > =
  ref(
    
      CrearUsuarioNuevoMutation.Variables.build(
        alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,estadoValidacion=estadoValidacion,estrellasPrestigio=estrellasPrestigio,rachaActualDias=rachaActualDias,tipoUsuario=tipoUsuario,ultimaActividad=ultimaActividad,
  
    block_
      )
    
  )

public suspend fun CrearUsuarioNuevoMutation.execute(
  
    alias: String,nombre: String,apellidoPaterno: String,apellidoMaterno: String,estadoValidacion: Boolean,estrellasPrestigio: Int,rachaActualDias: Int,tipoUsuario: Int,ultimaActividad: com.google.firebase.Timestamp,
  
    block_: CrearUsuarioNuevoMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CrearUsuarioNuevoMutation.Data,
    CrearUsuarioNuevoMutation.Variables
  > =
  ref(
    
      alias=alias,nombre=nombre,apellidoPaterno=apellidoPaterno,apellidoMaterno=apellidoMaterno,estadoValidacion=estadoValidacion,estrellasPrestigio=estrellasPrestigio,rachaActualDias=rachaActualDias,tipoUsuario=tipoUsuario,ultimaActividad=ultimaActividad,
  
    block_
    
  ).execute()


