
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



public interface AgregarTarjetaArchivoMutation :
    com.google.firebase.dataconnect.generated.GeneratedMutation<
      DefaultConnector,
      AgregarTarjetaArchivoMutation.Data,
      AgregarTarjetaArchivoMutation.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val archivoId: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val ordenSecuencia: Int,
    val contenidoTexto: String,
    val tipoFondo: String,
    val dataFondo: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val tarjeta_insert: TarjetaKey
  ) {
    
    
  }
  

  public companion object {
    public val operationName: String = "AgregarTarjetaArchivo"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun AgregarTarjetaArchivoMutation.ref(
  
    archivoId: java.util.UUID,ordenSecuencia: Int,contenidoTexto: String,tipoFondo: String,dataFondo: String,
  
  
): com.google.firebase.dataconnect.MutationRef<
    AgregarTarjetaArchivoMutation.Data,
    AgregarTarjetaArchivoMutation.Variables
  > =
  ref(
    
      AgregarTarjetaArchivoMutation.Variables(
        archivoId=archivoId,ordenSecuencia=ordenSecuencia,contenidoTexto=contenidoTexto,tipoFondo=tipoFondo,dataFondo=dataFondo,
  
      )
    
  )

public suspend fun AgregarTarjetaArchivoMutation.execute(
  
    archivoId: java.util.UUID,ordenSecuencia: Int,contenidoTexto: String,tipoFondo: String,dataFondo: String,
  
  
  ): com.google.firebase.dataconnect.MutationResult<
    AgregarTarjetaArchivoMutation.Data,
    AgregarTarjetaArchivoMutation.Variables
  > =
  ref(
    
      archivoId=archivoId,ordenSecuencia=ordenSecuencia,contenidoTexto=contenidoTexto,tipoFondo=tipoFondo,dataFondo=dataFondo,
  
    
  ).execute()


