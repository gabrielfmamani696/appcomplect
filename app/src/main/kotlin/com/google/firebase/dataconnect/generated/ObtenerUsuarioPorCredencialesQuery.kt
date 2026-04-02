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

import com.google.firebase.dataconnect.QueryRef
import com.google.firebase.dataconnect.QueryResult
import com.google.firebase.dataconnect.serializers.UUIDSerializer
import java.util.UUID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull as _flow_filterNotNull
import kotlinx.coroutines.flow.map as _flow_map
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationStrategy
import kotlinx.serialization.serializer

public interface ObtenerUsuarioPorCredencialesQuery :
    GeneratedQuery<
      DefaultConnector,
      ObtenerUsuarioPorCredencialesQuery.Data,
      ObtenerUsuarioPorCredencialesQuery.Variables
    >
{
  
    @Serializable
  public data class Variables(
    val numeroCelular: String,
    val nombre: String,
    val apellidoPaterno: String
  )

    @Serializable
  public data class Data(
    val usuarios: List<UsuariosItem>
  ) {
    
        @Serializable
  public data class UsuariosItem(
    val id: @Serializable(with = UUIDSerializer::class) UUID,
    val alias: String,
    val avatar: Avatar?
  ) {
    
        @Serializable
  public data class Avatar(
    val imagenUrl: String
  )
      
  }
  }

  public companion object {
    public val operationName: String = "ObtenerUsuarioPorCredenciales"

    public val dataDeserializer: DeserializationStrategy<Data> =
      serializer()

    public val variablesSerializer: SerializationStrategy<Variables> =
      serializer()
  }
}

public fun ObtenerUsuarioPorCredencialesQuery.ref(
    numeroCelular: String,
    nombre: String,
    apellidoPaterno: String,
): QueryRef<
    ObtenerUsuarioPorCredencialesQuery.Data,
    ObtenerUsuarioPorCredencialesQuery.Variables
  > =
  ref(
      ObtenerUsuarioPorCredencialesQuery.Variables(
        numeroCelular=numeroCelular,
        nombre=nombre,
        apellidoPaterno=apellidoPaterno,
      )
  )

public suspend fun ObtenerUsuarioPorCredencialesQuery.execute(
    numeroCelular: String,
    nombre: String,
    apellidoPaterno: String,
  ): QueryResult<
    ObtenerUsuarioPorCredencialesQuery.Data,
    ObtenerUsuarioPorCredencialesQuery.Variables
  > =
  ref(
      numeroCelular=numeroCelular,
      nombre=nombre,
      apellidoPaterno=apellidoPaterno,
  ).execute()

  public fun ObtenerUsuarioPorCredencialesQuery.flow(
      numeroCelular: String,
      nombre: String,
      apellidoPaterno: String,
    ): Flow<ObtenerUsuarioPorCredencialesQuery.Data> =
    ref(
          numeroCelular=numeroCelular,
          nombre=nombre,
          apellidoPaterno=apellidoPaterno,
      ).subscribe()
      .flow
      ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
      ._flow_filterNotNull()
      ._flow_map { it.data }
