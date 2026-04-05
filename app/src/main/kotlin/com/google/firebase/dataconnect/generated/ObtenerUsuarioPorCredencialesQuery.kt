
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


import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map


public interface ObtenerUsuarioPorCredencialesQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerUsuarioPorCredencialesQuery.Data,
      ObtenerUsuarioPorCredencialesQuery.Variables
    >
{
  
    @kotlinx.serialization.Serializable
  public data class Variables(
  
    val numeroCelular: String,
    val nombre: String,
    val apellidoPaterno: String
  ) {
    
    
  }
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val usuarios: List<UsuariosItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class UsuariosItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val alias: String,
    val avatar: Avatar?
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Avatar(
  
    val imagenUrl: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerUsuarioPorCredenciales"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerUsuarioPorCredencialesQuery.ref(
  
    numeroCelular: String,nombre: String,apellidoPaterno: String,
  
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerUsuarioPorCredencialesQuery.Data,
    ObtenerUsuarioPorCredencialesQuery.Variables
  > =
  ref(
    
      ObtenerUsuarioPorCredencialesQuery.Variables(
        numeroCelular=numeroCelular,nombre=nombre,apellidoPaterno=apellidoPaterno,
  
      )
    
  )

public suspend fun ObtenerUsuarioPorCredencialesQuery.execute(
  
    numeroCelular: String,nombre: String,apellidoPaterno: String,
  
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerUsuarioPorCredencialesQuery.Data,
    ObtenerUsuarioPorCredencialesQuery.Variables
  > =
  ref(
    
      numeroCelular=numeroCelular,nombre=nombre,apellidoPaterno=apellidoPaterno,
  
    
  ).execute()


  public fun ObtenerUsuarioPorCredencialesQuery.flow(
    
      numeroCelular: String,nombre: String,apellidoPaterno: String,
  
    
    ): kotlinx.coroutines.flow.Flow<ObtenerUsuarioPorCredencialesQuery.Data> =
    ref(
        
          numeroCelular=numeroCelular,nombre=nombre,apellidoPaterno=apellidoPaterno,
  
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

