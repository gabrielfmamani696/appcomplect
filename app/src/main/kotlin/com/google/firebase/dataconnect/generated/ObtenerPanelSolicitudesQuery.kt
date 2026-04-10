
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


public interface ObtenerPanelSolicitudesQuery :
    com.google.firebase.dataconnect.generated.GeneratedQuery<
      DefaultConnector,
      ObtenerPanelSolicitudesQuery.Data,
      Unit
    >
{
  

  
    @kotlinx.serialization.Serializable
  public data class Data(
  
    val solicitudValidacions: List<SolicitudValidacionsItem>
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class SolicitudValidacionsItem(
  
    val id: @kotlinx.serialization.Serializable(with = com.google.firebase.dataconnect.serializers.UUIDSerializer::class) java.util.UUID,
    val usuario: Usuario
  ) {
    
      
        @kotlinx.serialization.Serializable
  public data class Usuario(
  
    val nombre: String,
    val apellidoPaterno: String
  ) {
    
    
  }
      
    
    
  }
      
    
    
  }
  

  public companion object {
    public val operationName: String = "ObtenerPanelSolicitudes"

    public val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data> =
      kotlinx.serialization.serializer()

    public val variablesSerializer: kotlinx.serialization.SerializationStrategy<Unit> =
      kotlinx.serialization.serializer()
  }
}

public fun ObtenerPanelSolicitudesQuery.ref(
  
): com.google.firebase.dataconnect.QueryRef<
    ObtenerPanelSolicitudesQuery.Data,
    Unit
  > =
  ref(
    
      Unit
    
  )

public suspend fun ObtenerPanelSolicitudesQuery.execute(
  
  ): com.google.firebase.dataconnect.QueryResult<
    ObtenerPanelSolicitudesQuery.Data,
    Unit
  > =
  ref(
    
  ).execute()


  public fun ObtenerPanelSolicitudesQuery.flow(
    
    ): kotlinx.coroutines.flow.Flow<ObtenerPanelSolicitudesQuery.Data> =
    ref(
        
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }

