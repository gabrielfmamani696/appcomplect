
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

import com.google.firebase.dataconnect.getInstance as _fdcGetInstance

public interface DefaultConnector : com.google.firebase.dataconnect.generated.GeneratedConnector<DefaultConnector> {
  override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect

  
    public val crearUsuarioNuevo: CrearUsuarioNuevoMutation
  
    public val listarArchivosPublicos: ListarArchivosPublicosQuery
  
    public val listarAvatares: ListarAvataresQuery
  
    public val obtenerPerfilCompleto: ObtenerPerfilCompletoQuery
  
    public val obtenerUsuarioPorId: ObtenerUsuarioPorIdQuery
  
    public val seedOpcionAvatarData: SeedOpcionAvatarDataMutation
  

  public companion object {
    @Suppress("MemberVisibilityCanBePrivate")
    public val config: com.google.firebase.dataconnect.ConnectorConfig = com.google.firebase.dataconnect.ConnectorConfig(
      connector = "default",
      location = "us-east4",
      serviceId = "appcomplect-d11b1-service",
    )

    public fun getInstance(
      dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
    ):DefaultConnector = synchronized(instances) {
      instances.getOrPut(dataConnect) {
        DefaultConnectorImpl(dataConnect)
      }
    }

    private val instances = java.util.WeakHashMap<com.google.firebase.dataconnect.FirebaseDataConnect, DefaultConnectorImpl>()
  }
}

public val DefaultConnector.Companion.instance:DefaultConnector
  get() = getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(config))

public fun DefaultConnector.Companion.getInstance(
  settings: com.google.firebase.dataconnect.DataConnectSettings = com.google.firebase.dataconnect.DataConnectSettings()
):DefaultConnector =
  getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(config, settings))

public fun DefaultConnector.Companion.getInstance(
  app: com.google.firebase.FirebaseApp,
  settings: com.google.firebase.dataconnect.DataConnectSettings = com.google.firebase.dataconnect.DataConnectSettings()
):DefaultConnector =
  getInstance(com.google.firebase.dataconnect.FirebaseDataConnect._fdcGetInstance(app, config, settings))

private class DefaultConnectorImpl(
  override val dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect
) : DefaultConnector {
  
    override val crearUsuarioNuevo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CrearUsuarioNuevoMutationImpl(this)
    }
  
    override val listarArchivosPublicos by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarArchivosPublicosQueryImpl(this)
    }
  
    override val listarAvatares by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarAvataresQueryImpl(this)
    }
  
    override val obtenerPerfilCompleto by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerPerfilCompletoQueryImpl(this)
    }
  
    override val obtenerUsuarioPorId by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerUsuarioPorIdQueryImpl(this)
    }
  
    override val seedOpcionAvatarData by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SeedOpcionAvatarDataMutationImpl(this)
    }
  

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun operations(): List<com.google.firebase.dataconnect.generated.GeneratedOperation<DefaultConnector, *, *>> =
    queries() + mutations()

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun mutations(): List<com.google.firebase.dataconnect.generated.GeneratedMutation<DefaultConnector, *, *>> =
    listOf(
      crearUsuarioNuevo,
        seedOpcionAvatarData,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun queries(): List<com.google.firebase.dataconnect.generated.GeneratedQuery<DefaultConnector, *, *>> =
    listOf(
      listarArchivosPublicos,
        listarAvatares,
        obtenerPerfilCompleto,
        obtenerUsuarioPorId,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(dataConnect: com.google.firebase.dataconnect.FirebaseDataConnect) =
    DefaultConnectorImpl(dataConnect)

  override fun equals(other: Any?): Boolean =
    other is DefaultConnectorImpl &&
    other.dataConnect == dataConnect

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "DefaultConnectorImpl",
      dataConnect,
    )

  override fun toString(): String =
    "DefaultConnectorImpl(dataConnect=$dataConnect)"
}



private open class DefaultConnectorGeneratedQueryImpl<Data, Variables>(
  override val connector: DefaultConnector,
  override val operationName: String,
  override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
  override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedQuery<DefaultConnector, Data, Variables> {

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(
    connector: DefaultConnector,
    operationName: String,
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
  ) =
    DefaultConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewVariables> withVariablesSerializer(
    variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
  ) =
    DefaultConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewData> withDataDeserializer(
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
  ) =
    DefaultConnectorGeneratedQueryImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun equals(other: Any?): Boolean =
    other is DefaultConnectorGeneratedQueryImpl<*,*> &&
    other.connector == connector &&
    other.operationName == operationName &&
    other.dataDeserializer == dataDeserializer &&
    other.variablesSerializer == variablesSerializer

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "DefaultConnectorGeneratedQueryImpl",
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun toString(): String =
    "DefaultConnectorGeneratedQueryImpl(" +
    "operationName=$operationName, " +
    "dataDeserializer=$dataDeserializer, " +
    "variablesSerializer=$variablesSerializer, " +
    "connector=$connector)"
}

private open class DefaultConnectorGeneratedMutationImpl<Data, Variables>(
  override val connector: DefaultConnector,
  override val operationName: String,
  override val dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
  override val variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
) : com.google.firebase.dataconnect.generated.GeneratedMutation<DefaultConnector, Data, Variables> {

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun copy(
    connector: DefaultConnector,
    operationName: String,
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<Data>,
    variablesSerializer: kotlinx.serialization.SerializationStrategy<Variables>,
  ) =
    DefaultConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewVariables> withVariablesSerializer(
    variablesSerializer: kotlinx.serialization.SerializationStrategy<NewVariables>
  ) =
    DefaultConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun <NewData> withDataDeserializer(
    dataDeserializer: kotlinx.serialization.DeserializationStrategy<NewData>
  ) =
    DefaultConnectorGeneratedMutationImpl(
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun equals(other: Any?): Boolean =
    other is DefaultConnectorGeneratedMutationImpl<*,*> &&
    other.connector == connector &&
    other.operationName == operationName &&
    other.dataDeserializer == dataDeserializer &&
    other.variablesSerializer == variablesSerializer

  override fun hashCode(): Int =
    java.util.Objects.hash(
      "DefaultConnectorGeneratedMutationImpl",
      connector, operationName, dataDeserializer, variablesSerializer
    )

  override fun toString(): String =
    "DefaultConnectorGeneratedMutationImpl(" +
    "operationName=$operationName, " +
    "dataDeserializer=$dataDeserializer, " +
    "variablesSerializer=$variablesSerializer, " +
    "connector=$connector)"
}



private class CrearUsuarioNuevoMutationImpl(
  connector: DefaultConnector
):
  CrearUsuarioNuevoMutation,
  DefaultConnectorGeneratedMutationImpl<
      CrearUsuarioNuevoMutation.Data,
      CrearUsuarioNuevoMutation.Variables
  >(
    connector,
    CrearUsuarioNuevoMutation.Companion.operationName,
    CrearUsuarioNuevoMutation.Companion.dataDeserializer,
    CrearUsuarioNuevoMutation.Companion.variablesSerializer,
  )


private class ListarArchivosPublicosQueryImpl(
  connector: DefaultConnector
):
  ListarArchivosPublicosQuery,
  DefaultConnectorGeneratedQueryImpl<
      ListarArchivosPublicosQuery.Data,
      Unit
  >(
    connector,
    ListarArchivosPublicosQuery.Companion.operationName,
    ListarArchivosPublicosQuery.Companion.dataDeserializer,
    ListarArchivosPublicosQuery.Companion.variablesSerializer,
  )


private class ListarAvataresQueryImpl(
  connector: DefaultConnector
):
  ListarAvataresQuery,
  DefaultConnectorGeneratedQueryImpl<
      ListarAvataresQuery.Data,
      Unit
  >(
    connector,
    ListarAvataresQuery.Companion.operationName,
    ListarAvataresQuery.Companion.dataDeserializer,
    ListarAvataresQuery.Companion.variablesSerializer,
  )


private class ObtenerPerfilCompletoQueryImpl(
  connector: DefaultConnector
):
  ObtenerPerfilCompletoQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerPerfilCompletoQuery.Data,
      ObtenerPerfilCompletoQuery.Variables
  >(
    connector,
    ObtenerPerfilCompletoQuery.Companion.operationName,
    ObtenerPerfilCompletoQuery.Companion.dataDeserializer,
    ObtenerPerfilCompletoQuery.Companion.variablesSerializer,
  )


private class ObtenerUsuarioPorIdQueryImpl(
  connector: DefaultConnector
):
  ObtenerUsuarioPorIdQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerUsuarioPorIdQuery.Data,
      ObtenerUsuarioPorIdQuery.Variables
  >(
    connector,
    ObtenerUsuarioPorIdQuery.Companion.operationName,
    ObtenerUsuarioPorIdQuery.Companion.dataDeserializer,
    ObtenerUsuarioPorIdQuery.Companion.variablesSerializer,
  )


private class SeedOpcionAvatarDataMutationImpl(
  connector: DefaultConnector
):
  SeedOpcionAvatarDataMutation,
  DefaultConnectorGeneratedMutationImpl<
      SeedOpcionAvatarDataMutation.Data,
      Unit
  >(
    connector,
    SeedOpcionAvatarDataMutation.Companion.operationName,
    SeedOpcionAvatarDataMutation.Companion.dataDeserializer,
    SeedOpcionAvatarDataMutation.Companion.variablesSerializer,
  )


