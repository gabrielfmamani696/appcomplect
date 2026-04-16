
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


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface ObtenerContenidoArchivoQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerContenidoArchivoQuery.Data,
      ObtenerContenidoArchivoQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val idArchivo: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val archivo: Archivo?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Archivo(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val titulo: String,
    val tema: String,
    val descripcion: String,
    val fechaCreacion: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.TimestampSerializer::class) com.google.firebase.Timestamp,
    val imagenUrl: String?,
    val autorOriginal: String?,
    val licencia: String?,
    val tarjetas_on_archivo: List<TarjetasOnArchivoItem>,
    val cuestionarios_on_archivo: List<CuestionariosOnArchivoItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class TarjetasOnArchivoItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val ordenSecuencia: Int,
    val contenidoTexto: String,
    val tipoFondo: String,
    val dataFondo: String
  ) {
    
    
  }
      
        @kotlinx.serialization.Serializable
  public data class CuestionariosOnArchivoItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val tituloQuiz: String,
    val preguntas_on_cuestionario: List<PreguntasOnCuestionarioItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class PreguntasOnCuestionarioItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val enunciado: String,
    val respuestas_on_pregunta: List<RespuestasOnPreguntaItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class RespuestasOnPreguntaItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val textoOpcion: String,
    val esCorrecta: Boolean
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerContenidoArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerContenidoArchivoQuery.ref(
  
    idArchivo: java.util.UUID,
  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerContenidoArchivoQuery.Data,
    ObtenerContenidoArchivoQuery.Variables
  > =
  ref(
    
      ObtenerContenidoArchivoQuery.Variables(
        idArchivo=idArchivo,
  
      )
    
  )

public suspend fun ObtenerContenidoArchivoQuery.execute(
  
    idArchivo: java.util.UUID,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerContenidoArchivoQuery.Data,
    ObtenerContenidoArchivoQuery.Variables
  > =
  ref(
    
      idArchivo=idArchivo,
  
    
  ).execute()


  public fun ObtenerContenidoArchivoQuery.flow(
    
      idArchivo: java.util.UUID,
  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerContenidoArchivoQuery.Data> =
    ref(
        
          idArchivo=idArchivo,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

