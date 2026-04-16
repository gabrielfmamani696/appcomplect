
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



public interface CrearEspacioAprendizajeMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      CrearEspacioAprendizajeMutation.Data,
      CrearEspacioAprendizajeMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val usuarioId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val nombreEspacio: String,
    val codigoAcceso: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val espacioAprendizaje_insert: EspacioAprendizajeKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "CrearEspacioAprendizaje"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun CrearEspacioAprendizajeMutation.ref(
  
    usuarioId: java.util.UUID,nombreEspacio: String,codigoAcceso: String,
  
  
): com.google.firebase.dataconnect.MutationRef<
    CrearEspacioAprendizajeMutation.Data,
    CrearEspacioAprendizajeMutation.Variables
  > =
  ref(
    
      CrearEspacioAprendizajeMutation.Variables(
        usuarioId=usuarioId,nombreEspacio=nombreEspacio,codigoAcceso=codigoAcceso,
  
      )
    
  )

public suspend fun CrearEspacioAprendizajeMutation.execute(
  
    usuarioId: java.util.UUID,nombreEspacio: String,codigoAcceso: String,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    CrearEspacioAprendizajeMutation.Data,
    CrearEspacioAprendizajeMutation.Variables
  > =
  ref(
    
      usuarioId=usuarioId,nombreEspacio=nombreEspacio,codigoAcceso=codigoAcceso,
  
    
  ).execute()


