
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



public interface CrearArchivoDivulgacionMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      CrearArchivoDivulgacionMutation.Data,
      CrearArchivoDivulgacionMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val titulo: String,
    val tema: String,
    val descripcion: String,
    val fechaCreacion: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val imagenUrl: com.google.firebase.dataconnect.OptionalVariable<String?>,
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nivelRequeridoId: com.google.firebase.dataconnect.OptionalVariable<@kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var titulo: String
        public var tema: String
        public var descripcion: String
        public var fechaCreacion: com.google.firebase.Timestamp
        public var imagenUrl: String?
        public var usuarioId: java.util.UUID
        public var nivelRequeridoId: java.util.UUID?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          titulo: String,tema: String,descripcion: String,fechaCreacion: com.google.firebase.Timestamp,usuarioId: java.util.UUID,
          block_: Builder.() -> Unit
        ): Variables {
          var titulo= titulo
            var tema= tema
            var descripcion= descripcion
            var fechaCreacion= fechaCreacion
            var imagenUrl: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            var usuarioId= usuarioId
            var nivelRequeridoId: com.google.firebase.dataconnect.OptionalVariable<java.util.UUID?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var titulo: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { titulo = value_ }
              
            override var tema: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { tema = value_ }
              
            override var descripcion: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { descripcion = value_ }
              
            override var fechaCreacion: com.google.firebase.Timestamp
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { fechaCreacion = value_ }
              
            override var imagenUrl: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { imagenUrl = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            override var usuarioId: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { usuarioId = value_ }
              
            override var nivelRequeridoId: java.util.UUID?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { nivelRequeridoId = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              titulo=titulo,tema=tema,descripcion=descripcion,fechaCreacion=fechaCreacion,imagenUrl=imagenUrl,usuarioId=usuarioId,nivelRequeridoId=nivelRequeridoId,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val archivo_insert: ArchivoKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CrearArchivoDivulgacion"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CrearArchivoDivulgacionMutation.ref(
  
    titulo: String,tema: String,descripcion: String,fechaCreacion: com.google.firebase.Timestamp,usuarioId: java.util.UUID,
  
    block_: CrearArchivoDivulgacionMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    CrearArchivoDivulgacionMutation.Data,
    CrearArchivoDivulgacionMutation.Variables
  > =
  ref(
    
      CrearArchivoDivulgacionMutation.Variables.build(
        titulo=titulo,tema=tema,descripcion=descripcion,fechaCreacion=fechaCreacion,usuarioId=usuarioId,
  
    block_
      )
    
  )

public suspend fun CrearArchivoDivulgacionMutation.execute(
  
    titulo: String,tema: String,descripcion: String,fechaCreacion: com.google.firebase.Timestamp,usuarioId: java.util.UUID,
  
    block_: CrearArchivoDivulgacionMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    CrearArchivoDivulgacionMutation.Data,
    CrearArchivoDivulgacionMutation.Variables
  > =
  ref(
    
      titulo=titulo,tema=tema,descripcion=descripcion,fechaCreacion=fechaCreacion,usuarioId=usuarioId,
  
    block_
    
  ).execute()


