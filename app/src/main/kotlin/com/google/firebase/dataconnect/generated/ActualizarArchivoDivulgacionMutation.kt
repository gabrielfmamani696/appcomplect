
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



public interface ActualizarArchivoDivulgacionMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      ActualizarArchivoDivulgacionMutation.Data,
      ActualizarArchivoDivulgacionMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val titulo: String,
    val tema: String,
    val descripcion: String,
    val imagenUrl: com.google.firebase.dataconnect.OptionalVariable<String?>
  ) {
    
    
      
      @kotlin.DslMarker public annotation class BuilderDsl

      @BuilderDsl
      public interface Builder {
        public var id: java.util.UUID
        public var titulo: String
        public var tema: String
        public var descripcion: String
        public var imagenUrl: String?
        
      }

      public companion object {
        @Suppress("NAME_SHADOWING")
        public fun build(
          id: java.util.UUID,titulo: String,tema: String,descripcion: String,
          block_: Builder.() -> Unit
        ): Variables {
          var id= id
            var titulo= titulo
            var tema= tema
            var descripcion= descripcion
            var imagenUrl: com.google.firebase.dataconnect.OptionalVariable<String?> =
                com.google.firebase.dataconnect.OptionalVariable.Undefined
            

          return object : Builder {
            override var id: java.util.UUID
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { id = value_ }
              
            override var titulo: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { titulo = value_ }
              
            override var tema: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { tema = value_ }
              
            override var descripcion: String
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { descripcion = value_ }
              
            override var imagenUrl: String?
              get() = throw UnsupportedOperationException("getting builder values is not supported")
              set(value_) { imagenUrl = com.google.firebase.dataconnect.OptionalVariable.Value(value_) }
              
            
          }.apply(block_)
          .let {
            Variables(
              id=id,titulo=titulo,tema=tema,descripcion=descripcion,imagenUrl=imagenUrl,
            )
          }
        }
      }
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val archivo_update: ArchivoKey?
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "ActualizarArchivoDivulgacion"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ActualizarArchivoDivulgacionMutation.ref(
  
    id: java.util.UUID,titulo: String,tema: String,descripcion: String,
  
    block_: ActualizarArchivoDivulgacionMutation.Variables.Builder.() -> Unit = {}
  
): com.google.firebase.dataconnect.MutationRef<
    ActualizarArchivoDivulgacionMutation.Data,
    ActualizarArchivoDivulgacionMutation.Variables
  > =
  ref(
    
      ActualizarArchivoDivulgacionMutation.Variables.build(
        id=id,titulo=titulo,tema=tema,descripcion=descripcion,
  
    block_
      )
    
  )

public suspend fun ActualizarArchivoDivulgacionMutation.execute(
  
    id: java.util.UUID,titulo: String,tema: String,descripcion: String,
  
    block_: ActualizarArchivoDivulgacionMutation.Variables.Builder.() -> Unit = {}
  
  ): com.google.firebase.dataconnect.MutationResult<
    ActualizarArchivoDivulgacionMutation.Data,
    ActualizarArchivoDivulgacionMutation.Variables
  > =
  ref(
    
      id=id,titulo=titulo,tema=tema,descripcion=descripcion,
  
    block_
    
  ).execute()


