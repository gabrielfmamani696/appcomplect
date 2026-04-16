
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

  
    public val actualizarArchivoDivulgacion: ActualizarArchivoDivulgacionMutation
  
    public val actualizarArchivoEspacio: ActualizarArchivoEspacioMutation
  
    public val actualizarEstrellasUsuario: ActualizarEstrellasUsuarioMutation
  
    public val actualizarNivelUsuario: ActualizarNivelUsuarioMutation
  
    public val actualizarRachaUsuario: ActualizarRachaUsuarioMutation
  
    public val actualizarUsuarioPerfil: ActualizarUsuarioPerfilMutation
  
    public val agregarCuestionarioArchivo: AgregarCuestionarioArchivoMutation
  
    public val agregarPreguntaCuestionario: AgregarPreguntaCuestionarioMutation
  
    public val agregarRespuestaPregunta: AgregarRespuestaPreguntaMutation
  
    public val agregarTarjetaArchivo: AgregarTarjetaArchivoMutation
  
    public val buscarEspacioPorCodigo: BuscarEspacioPorCodigoQuery
  
    public val contarArchivosCompletadosDistintos: ContarArchivosCompletadosDistintosQuery
  
    public val contarIntentosUsuario: ContarIntentosUsuarioQuery
  
    public val crearArchivoDivulgacion: CrearArchivoDivulgacionMutation
  
    public val crearEspacioAprendizaje: CrearEspacioAprendizajeMutation
  
    public val crearSolicitudValidacion: CrearSolicitudValidacionMutation
  
    public val crearUsuarioNuevo: CrearUsuarioNuevoMutation
  
    public val eliminarArchivo: EliminarArchivoMutation
  
    public val eliminarCuestionariosDeArchivo: EliminarCuestionariosDeArchivoMutation
  
    public val eliminarPreguntasDeCuestionario: EliminarPreguntasDeCuestionarioMutation
  
    public val eliminarRespuestasDePregunta: EliminarRespuestasDePreguntaMutation
  
    public val eliminarTarjetasDeArchivo: EliminarTarjetasDeArchivoMutation
  
    public val listarArchivosPublicos: ListarArchivosPublicosQuery
  
    public val listarAvatares: ListarAvataresQuery
  
    public val listarInsigniasObtenidasUsuario: ListarInsigniasObtenidasUsuarioQuery
  
    public val listarInvestigadoresOrdenadosPorEstrellas: ListarInvestigadoresOrdenadosPorEstrellasQuery
  
    public val listarNiveles: ListarNivelesQuery
  
    public val listarRoles: ListarRolesQuery
  
    public val listarTodasInsignias: ListarTodasInsigniasQuery
  
    public val obtenerArchivoParaEdicion: ObtenerArchivoParaEdicionQuery
  
    public val obtenerContenidoArchivo: ObtenerContenidoArchivoQuery
  
    public val obtenerEspaciosPorMiembro: ObtenerEspaciosPorMiembroQuery
  
    public val obtenerEstadisticasArchivo: ObtenerEstadisticasArchivoQuery
  
    public val obtenerEstructuraLimpiezaArchivo: ObtenerEstructuraLimpiezaArchivoQuery
  
    public val obtenerInsigniasNotificadas: ObtenerInsigniasNotificadasQuery
  
    public val obtenerLeaderboard: ObtenerLeaderboardQuery
  
    public val obtenerMisEspacios: ObtenerMisEspaciosQuery
  
    public val obtenerPanelSolicitudes: ObtenerPanelSolicitudesQuery
  
    public val obtenerPerfilCompleto: ObtenerPerfilCompletoQuery
  
    public val obtenerRankingUsuario: ObtenerRankingUsuarioQuery
  
    public val obtenerUsuarioPorCredenciales: ObtenerUsuarioPorCredencialesQuery
  
    public val obtenerUsuarioPorId: ObtenerUsuarioPorIdQuery
  
    public val registrarIntentoExamen: RegistrarIntentoExamenMutation
  
    public val registrarLogroNotificado: RegistrarLogroNotificadoMutation
  
    public val seedOpcionAvatarData: SeedOpcionAvatarDataMutation
  
    public val seedRolesData: SeedRolesDataMutation
  
    public val unirseAEspacio: UnirseAEspacioMutation
  

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
  
    override val actualizarArchivoDivulgacion by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ActualizarArchivoDivulgacionMutationImpl(this)
    }
  
    override val actualizarArchivoEspacio by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ActualizarArchivoEspacioMutationImpl(this)
    }
  
    override val actualizarEstrellasUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ActualizarEstrellasUsuarioMutationImpl(this)
    }
  
    override val actualizarNivelUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ActualizarNivelUsuarioMutationImpl(this)
    }
  
    override val actualizarRachaUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ActualizarRachaUsuarioMutationImpl(this)
    }
  
    override val actualizarUsuarioPerfil by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ActualizarUsuarioPerfilMutationImpl(this)
    }
  
    override val agregarCuestionarioArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      AgregarCuestionarioArchivoMutationImpl(this)
    }
  
    override val agregarPreguntaCuestionario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      AgregarPreguntaCuestionarioMutationImpl(this)
    }
  
    override val agregarRespuestaPregunta by lazy(LazyThreadSafetyMode.PUBLICATION) {
      AgregarRespuestaPreguntaMutationImpl(this)
    }
  
    override val agregarTarjetaArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      AgregarTarjetaArchivoMutationImpl(this)
    }
  
    override val buscarEspacioPorCodigo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      BuscarEspacioPorCodigoQueryImpl(this)
    }
  
    override val contarArchivosCompletadosDistintos by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ContarArchivosCompletadosDistintosQueryImpl(this)
    }
  
    override val contarIntentosUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ContarIntentosUsuarioQueryImpl(this)
    }
  
    override val crearArchivoDivulgacion by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CrearArchivoDivulgacionMutationImpl(this)
    }
  
    override val crearEspacioAprendizaje by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CrearEspacioAprendizajeMutationImpl(this)
    }
  
    override val crearSolicitudValidacion by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CrearSolicitudValidacionMutationImpl(this)
    }
  
    override val crearUsuarioNuevo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      CrearUsuarioNuevoMutationImpl(this)
    }
  
    override val eliminarArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      EliminarArchivoMutationImpl(this)
    }
  
    override val eliminarCuestionariosDeArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      EliminarCuestionariosDeArchivoMutationImpl(this)
    }
  
    override val eliminarPreguntasDeCuestionario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      EliminarPreguntasDeCuestionarioMutationImpl(this)
    }
  
    override val eliminarRespuestasDePregunta by lazy(LazyThreadSafetyMode.PUBLICATION) {
      EliminarRespuestasDePreguntaMutationImpl(this)
    }
  
    override val eliminarTarjetasDeArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      EliminarTarjetasDeArchivoMutationImpl(this)
    }
  
    override val listarArchivosPublicos by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarArchivosPublicosQueryImpl(this)
    }
  
    override val listarAvatares by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarAvataresQueryImpl(this)
    }
  
    override val listarInsigniasObtenidasUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarInsigniasObtenidasUsuarioQueryImpl(this)
    }
  
    override val listarInvestigadoresOrdenadosPorEstrellas by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarInvestigadoresOrdenadosPorEstrellasQueryImpl(this)
    }
  
    override val listarNiveles by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarNivelesQueryImpl(this)
    }
  
    override val listarRoles by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarRolesQueryImpl(this)
    }
  
    override val listarTodasInsignias by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ListarTodasInsigniasQueryImpl(this)
    }
  
    override val obtenerArchivoParaEdicion by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerArchivoParaEdicionQueryImpl(this)
    }
  
    override val obtenerContenidoArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerContenidoArchivoQueryImpl(this)
    }
  
    override val obtenerEspaciosPorMiembro by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerEspaciosPorMiembroQueryImpl(this)
    }
  
    override val obtenerEstadisticasArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerEstadisticasArchivoQueryImpl(this)
    }
  
    override val obtenerEstructuraLimpiezaArchivo by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerEstructuraLimpiezaArchivoQueryImpl(this)
    }
  
    override val obtenerInsigniasNotificadas by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerInsigniasNotificadasQueryImpl(this)
    }
  
    override val obtenerLeaderboard by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerLeaderboardQueryImpl(this)
    }
  
    override val obtenerMisEspacios by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerMisEspaciosQueryImpl(this)
    }
  
    override val obtenerPanelSolicitudes by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerPanelSolicitudesQueryImpl(this)
    }
  
    override val obtenerPerfilCompleto by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerPerfilCompletoQueryImpl(this)
    }
  
    override val obtenerRankingUsuario by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerRankingUsuarioQueryImpl(this)
    }
  
    override val obtenerUsuarioPorCredenciales by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerUsuarioPorCredencialesQueryImpl(this)
    }
  
    override val obtenerUsuarioPorId by lazy(LazyThreadSafetyMode.PUBLICATION) {
      ObtenerUsuarioPorIdQueryImpl(this)
    }
  
    override val registrarIntentoExamen by lazy(LazyThreadSafetyMode.PUBLICATION) {
      RegistrarIntentoExamenMutationImpl(this)
    }
  
    override val registrarLogroNotificado by lazy(LazyThreadSafetyMode.PUBLICATION) {
      RegistrarLogroNotificadoMutationImpl(this)
    }
  
    override val seedOpcionAvatarData by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SeedOpcionAvatarDataMutationImpl(this)
    }
  
    override val seedRolesData by lazy(LazyThreadSafetyMode.PUBLICATION) {
      SeedRolesDataMutationImpl(this)
    }
  
    override val unirseAEspacio by lazy(LazyThreadSafetyMode.PUBLICATION) {
      UnirseAEspacioMutationImpl(this)
    }
  

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun operations(): List<com.google.firebase.dataconnect.generated.GeneratedOperation<DefaultConnector, *, *>> =
    queries() + mutations()

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun mutations(): List<com.google.firebase.dataconnect.generated.GeneratedMutation<DefaultConnector, *, *>> =
    listOf(
      actualizarArchivoDivulgacion,
        actualizarArchivoEspacio,
        actualizarEstrellasUsuario,
        actualizarNivelUsuario,
        actualizarRachaUsuario,
        actualizarUsuarioPerfil,
        agregarCuestionarioArchivo,
        agregarPreguntaCuestionario,
        agregarRespuestaPregunta,
        agregarTarjetaArchivo,
        crearArchivoDivulgacion,
        crearEspacioAprendizaje,
        crearSolicitudValidacion,
        crearUsuarioNuevo,
        eliminarArchivo,
        eliminarCuestionariosDeArchivo,
        eliminarPreguntasDeCuestionario,
        eliminarRespuestasDePregunta,
        eliminarTarjetasDeArchivo,
        registrarIntentoExamen,
        registrarLogroNotificado,
        seedOpcionAvatarData,
        seedRolesData,
        unirseAEspacio,
        
    )

  @com.google.firebase.dataconnect.ExperimentalFirebaseDataConnect
  override fun queries(): List<com.google.firebase.dataconnect.generated.GeneratedQuery<DefaultConnector, *, *>> =
    listOf(
      buscarEspacioPorCodigo,
        contarArchivosCompletadosDistintos,
        contarIntentosUsuario,
        listarArchivosPublicos,
        listarAvatares,
        listarInsigniasObtenidasUsuario,
        listarInvestigadoresOrdenadosPorEstrellas,
        listarNiveles,
        listarRoles,
        listarTodasInsignias,
        obtenerArchivoParaEdicion,
        obtenerContenidoArchivo,
        obtenerEspaciosPorMiembro,
        obtenerEstadisticasArchivo,
        obtenerEstructuraLimpiezaArchivo,
        obtenerInsigniasNotificadas,
        obtenerLeaderboard,
        obtenerMisEspacios,
        obtenerPanelSolicitudes,
        obtenerPerfilCompleto,
        obtenerRankingUsuario,
        obtenerUsuarioPorCredenciales,
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



private class ActualizarArchivoDivulgacionMutationImpl(
  connector: DefaultConnector
):
  ActualizarArchivoDivulgacionMutation,
  DefaultConnectorGeneratedMutationImpl<
      ActualizarArchivoDivulgacionMutation.Data,
      ActualizarArchivoDivulgacionMutation.Variables
  >(
    connector,
    ActualizarArchivoDivulgacionMutation.Companion.operationName,
    ActualizarArchivoDivulgacionMutation.Companion.dataDeserializer,
    ActualizarArchivoDivulgacionMutation.Companion.variablesSerializer,
  )


private class ActualizarArchivoEspacioMutationImpl(
  connector: DefaultConnector
):
  ActualizarArchivoEspacioMutation,
  DefaultConnectorGeneratedMutationImpl<
      ActualizarArchivoEspacioMutation.Data,
      ActualizarArchivoEspacioMutation.Variables
  >(
    connector,
    ActualizarArchivoEspacioMutation.Companion.operationName,
    ActualizarArchivoEspacioMutation.Companion.dataDeserializer,
    ActualizarArchivoEspacioMutation.Companion.variablesSerializer,
  )


private class ActualizarEstrellasUsuarioMutationImpl(
  connector: DefaultConnector
):
  ActualizarEstrellasUsuarioMutation,
  DefaultConnectorGeneratedMutationImpl<
      ActualizarEstrellasUsuarioMutation.Data,
      ActualizarEstrellasUsuarioMutation.Variables
  >(
    connector,
    ActualizarEstrellasUsuarioMutation.Companion.operationName,
    ActualizarEstrellasUsuarioMutation.Companion.dataDeserializer,
    ActualizarEstrellasUsuarioMutation.Companion.variablesSerializer,
  )


private class ActualizarNivelUsuarioMutationImpl(
  connector: DefaultConnector
):
  ActualizarNivelUsuarioMutation,
  DefaultConnectorGeneratedMutationImpl<
      ActualizarNivelUsuarioMutation.Data,
      ActualizarNivelUsuarioMutation.Variables
  >(
    connector,
    ActualizarNivelUsuarioMutation.Companion.operationName,
    ActualizarNivelUsuarioMutation.Companion.dataDeserializer,
    ActualizarNivelUsuarioMutation.Companion.variablesSerializer,
  )


private class ActualizarRachaUsuarioMutationImpl(
  connector: DefaultConnector
):
  ActualizarRachaUsuarioMutation,
  DefaultConnectorGeneratedMutationImpl<
      ActualizarRachaUsuarioMutation.Data,
      ActualizarRachaUsuarioMutation.Variables
  >(
    connector,
    ActualizarRachaUsuarioMutation.Companion.operationName,
    ActualizarRachaUsuarioMutation.Companion.dataDeserializer,
    ActualizarRachaUsuarioMutation.Companion.variablesSerializer,
  )


private class ActualizarUsuarioPerfilMutationImpl(
  connector: DefaultConnector
):
  ActualizarUsuarioPerfilMutation,
  DefaultConnectorGeneratedMutationImpl<
      ActualizarUsuarioPerfilMutation.Data,
      ActualizarUsuarioPerfilMutation.Variables
  >(
    connector,
    ActualizarUsuarioPerfilMutation.Companion.operationName,
    ActualizarUsuarioPerfilMutation.Companion.dataDeserializer,
    ActualizarUsuarioPerfilMutation.Companion.variablesSerializer,
  )


private class AgregarCuestionarioArchivoMutationImpl(
  connector: DefaultConnector
):
  AgregarCuestionarioArchivoMutation,
  DefaultConnectorGeneratedMutationImpl<
      AgregarCuestionarioArchivoMutation.Data,
      AgregarCuestionarioArchivoMutation.Variables
  >(
    connector,
    AgregarCuestionarioArchivoMutation.Companion.operationName,
    AgregarCuestionarioArchivoMutation.Companion.dataDeserializer,
    AgregarCuestionarioArchivoMutation.Companion.variablesSerializer,
  )


private class AgregarPreguntaCuestionarioMutationImpl(
  connector: DefaultConnector
):
  AgregarPreguntaCuestionarioMutation,
  DefaultConnectorGeneratedMutationImpl<
      AgregarPreguntaCuestionarioMutation.Data,
      AgregarPreguntaCuestionarioMutation.Variables
  >(
    connector,
    AgregarPreguntaCuestionarioMutation.Companion.operationName,
    AgregarPreguntaCuestionarioMutation.Companion.dataDeserializer,
    AgregarPreguntaCuestionarioMutation.Companion.variablesSerializer,
  )


private class AgregarRespuestaPreguntaMutationImpl(
  connector: DefaultConnector
):
  AgregarRespuestaPreguntaMutation,
  DefaultConnectorGeneratedMutationImpl<
      AgregarRespuestaPreguntaMutation.Data,
      AgregarRespuestaPreguntaMutation.Variables
  >(
    connector,
    AgregarRespuestaPreguntaMutation.Companion.operationName,
    AgregarRespuestaPreguntaMutation.Companion.dataDeserializer,
    AgregarRespuestaPreguntaMutation.Companion.variablesSerializer,
  )


private class AgregarTarjetaArchivoMutationImpl(
  connector: DefaultConnector
):
  AgregarTarjetaArchivoMutation,
  DefaultConnectorGeneratedMutationImpl<
      AgregarTarjetaArchivoMutation.Data,
      AgregarTarjetaArchivoMutation.Variables
  >(
    connector,
    AgregarTarjetaArchivoMutation.Companion.operationName,
    AgregarTarjetaArchivoMutation.Companion.dataDeserializer,
    AgregarTarjetaArchivoMutation.Companion.variablesSerializer,
  )


private class BuscarEspacioPorCodigoQueryImpl(
  connector: DefaultConnector
):
  BuscarEspacioPorCodigoQuery,
  DefaultConnectorGeneratedQueryImpl<
      BuscarEspacioPorCodigoQuery.Data,
      BuscarEspacioPorCodigoQuery.Variables
  >(
    connector,
    BuscarEspacioPorCodigoQuery.Companion.operationName,
    BuscarEspacioPorCodigoQuery.Companion.dataDeserializer,
    BuscarEspacioPorCodigoQuery.Companion.variablesSerializer,
  )


private class ContarArchivosCompletadosDistintosQueryImpl(
  connector: DefaultConnector
):
  ContarArchivosCompletadosDistintosQuery,
  DefaultConnectorGeneratedQueryImpl<
      ContarArchivosCompletadosDistintosQuery.Data,
      ContarArchivosCompletadosDistintosQuery.Variables
  >(
    connector,
    ContarArchivosCompletadosDistintosQuery.Companion.operationName,
    ContarArchivosCompletadosDistintosQuery.Companion.dataDeserializer,
    ContarArchivosCompletadosDistintosQuery.Companion.variablesSerializer,
  )


private class ContarIntentosUsuarioQueryImpl(
  connector: DefaultConnector
):
  ContarIntentosUsuarioQuery,
  DefaultConnectorGeneratedQueryImpl<
      ContarIntentosUsuarioQuery.Data,
      ContarIntentosUsuarioQuery.Variables
  >(
    connector,
    ContarIntentosUsuarioQuery.Companion.operationName,
    ContarIntentosUsuarioQuery.Companion.dataDeserializer,
    ContarIntentosUsuarioQuery.Companion.variablesSerializer,
  )


private class CrearArchivoDivulgacionMutationImpl(
  connector: DefaultConnector
):
  CrearArchivoDivulgacionMutation,
  DefaultConnectorGeneratedMutationImpl<
      CrearArchivoDivulgacionMutation.Data,
      CrearArchivoDivulgacionMutation.Variables
  >(
    connector,
    CrearArchivoDivulgacionMutation.Companion.operationName,
    CrearArchivoDivulgacionMutation.Companion.dataDeserializer,
    CrearArchivoDivulgacionMutation.Companion.variablesSerializer,
  )


private class CrearEspacioAprendizajeMutationImpl(
  connector: DefaultConnector
):
  CrearEspacioAprendizajeMutation,
  DefaultConnectorGeneratedMutationImpl<
      CrearEspacioAprendizajeMutation.Data,
      CrearEspacioAprendizajeMutation.Variables
  >(
    connector,
    CrearEspacioAprendizajeMutation.Companion.operationName,
    CrearEspacioAprendizajeMutation.Companion.dataDeserializer,
    CrearEspacioAprendizajeMutation.Companion.variablesSerializer,
  )


private class CrearSolicitudValidacionMutationImpl(
  connector: DefaultConnector
):
  CrearSolicitudValidacionMutation,
  DefaultConnectorGeneratedMutationImpl<
      CrearSolicitudValidacionMutation.Data,
      CrearSolicitudValidacionMutation.Variables
  >(
    connector,
    CrearSolicitudValidacionMutation.Companion.operationName,
    CrearSolicitudValidacionMutation.Companion.dataDeserializer,
    CrearSolicitudValidacionMutation.Companion.variablesSerializer,
  )


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


private class EliminarArchivoMutationImpl(
  connector: DefaultConnector
):
  EliminarArchivoMutation,
  DefaultConnectorGeneratedMutationImpl<
      EliminarArchivoMutation.Data,
      EliminarArchivoMutation.Variables
  >(
    connector,
    EliminarArchivoMutation.Companion.operationName,
    EliminarArchivoMutation.Companion.dataDeserializer,
    EliminarArchivoMutation.Companion.variablesSerializer,
  )


private class EliminarCuestionariosDeArchivoMutationImpl(
  connector: DefaultConnector
):
  EliminarCuestionariosDeArchivoMutation,
  DefaultConnectorGeneratedMutationImpl<
      EliminarCuestionariosDeArchivoMutation.Data,
      EliminarCuestionariosDeArchivoMutation.Variables
  >(
    connector,
    EliminarCuestionariosDeArchivoMutation.Companion.operationName,
    EliminarCuestionariosDeArchivoMutation.Companion.dataDeserializer,
    EliminarCuestionariosDeArchivoMutation.Companion.variablesSerializer,
  )


private class EliminarPreguntasDeCuestionarioMutationImpl(
  connector: DefaultConnector
):
  EliminarPreguntasDeCuestionarioMutation,
  DefaultConnectorGeneratedMutationImpl<
      EliminarPreguntasDeCuestionarioMutation.Data,
      EliminarPreguntasDeCuestionarioMutation.Variables
  >(
    connector,
    EliminarPreguntasDeCuestionarioMutation.Companion.operationName,
    EliminarPreguntasDeCuestionarioMutation.Companion.dataDeserializer,
    EliminarPreguntasDeCuestionarioMutation.Companion.variablesSerializer,
  )


private class EliminarRespuestasDePreguntaMutationImpl(
  connector: DefaultConnector
):
  EliminarRespuestasDePreguntaMutation,
  DefaultConnectorGeneratedMutationImpl<
      EliminarRespuestasDePreguntaMutation.Data,
      EliminarRespuestasDePreguntaMutation.Variables
  >(
    connector,
    EliminarRespuestasDePreguntaMutation.Companion.operationName,
    EliminarRespuestasDePreguntaMutation.Companion.dataDeserializer,
    EliminarRespuestasDePreguntaMutation.Companion.variablesSerializer,
  )


private class EliminarTarjetasDeArchivoMutationImpl(
  connector: DefaultConnector
):
  EliminarTarjetasDeArchivoMutation,
  DefaultConnectorGeneratedMutationImpl<
      EliminarTarjetasDeArchivoMutation.Data,
      EliminarTarjetasDeArchivoMutation.Variables
  >(
    connector,
    EliminarTarjetasDeArchivoMutation.Companion.operationName,
    EliminarTarjetasDeArchivoMutation.Companion.dataDeserializer,
    EliminarTarjetasDeArchivoMutation.Companion.variablesSerializer,
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


private class ListarInsigniasObtenidasUsuarioQueryImpl(
  connector: DefaultConnector
):
  ListarInsigniasObtenidasUsuarioQuery,
  DefaultConnectorGeneratedQueryImpl<
      ListarInsigniasObtenidasUsuarioQuery.Data,
      ListarInsigniasObtenidasUsuarioQuery.Variables
  >(
    connector,
    ListarInsigniasObtenidasUsuarioQuery.Companion.operationName,
    ListarInsigniasObtenidasUsuarioQuery.Companion.dataDeserializer,
    ListarInsigniasObtenidasUsuarioQuery.Companion.variablesSerializer,
  )


private class ListarInvestigadoresOrdenadosPorEstrellasQueryImpl(
  connector: DefaultConnector
):
  ListarInvestigadoresOrdenadosPorEstrellasQuery,
  DefaultConnectorGeneratedQueryImpl<
      ListarInvestigadoresOrdenadosPorEstrellasQuery.Data,
      Unit
  >(
    connector,
    ListarInvestigadoresOrdenadosPorEstrellasQuery.Companion.operationName,
    ListarInvestigadoresOrdenadosPorEstrellasQuery.Companion.dataDeserializer,
    ListarInvestigadoresOrdenadosPorEstrellasQuery.Companion.variablesSerializer,
  )


private class ListarNivelesQueryImpl(
  connector: DefaultConnector
):
  ListarNivelesQuery,
  DefaultConnectorGeneratedQueryImpl<
      ListarNivelesQuery.Data,
      Unit
  >(
    connector,
    ListarNivelesQuery.Companion.operationName,
    ListarNivelesQuery.Companion.dataDeserializer,
    ListarNivelesQuery.Companion.variablesSerializer,
  )


private class ListarRolesQueryImpl(
  connector: DefaultConnector
):
  ListarRolesQuery,
  DefaultConnectorGeneratedQueryImpl<
      ListarRolesQuery.Data,
      Unit
  >(
    connector,
    ListarRolesQuery.Companion.operationName,
    ListarRolesQuery.Companion.dataDeserializer,
    ListarRolesQuery.Companion.variablesSerializer,
  )


private class ListarTodasInsigniasQueryImpl(
  connector: DefaultConnector
):
  ListarTodasInsigniasQuery,
  DefaultConnectorGeneratedQueryImpl<
      ListarTodasInsigniasQuery.Data,
      Unit
  >(
    connector,
    ListarTodasInsigniasQuery.Companion.operationName,
    ListarTodasInsigniasQuery.Companion.dataDeserializer,
    ListarTodasInsigniasQuery.Companion.variablesSerializer,
  )


private class ObtenerArchivoParaEdicionQueryImpl(
  connector: DefaultConnector
):
  ObtenerArchivoParaEdicionQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerArchivoParaEdicionQuery.Data,
      ObtenerArchivoParaEdicionQuery.Variables
  >(
    connector,
    ObtenerArchivoParaEdicionQuery.Companion.operationName,
    ObtenerArchivoParaEdicionQuery.Companion.dataDeserializer,
    ObtenerArchivoParaEdicionQuery.Companion.variablesSerializer,
  )


private class ObtenerContenidoArchivoQueryImpl(
  connector: DefaultConnector
):
  ObtenerContenidoArchivoQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerContenidoArchivoQuery.Data,
      ObtenerContenidoArchivoQuery.Variables
  >(
    connector,
    ObtenerContenidoArchivoQuery.Companion.operationName,
    ObtenerContenidoArchivoQuery.Companion.dataDeserializer,
    ObtenerContenidoArchivoQuery.Companion.variablesSerializer,
  )


private class ObtenerEspaciosPorMiembroQueryImpl(
  connector: DefaultConnector
):
  ObtenerEspaciosPorMiembroQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerEspaciosPorMiembroQuery.Data,
      ObtenerEspaciosPorMiembroQuery.Variables
  >(
    connector,
    ObtenerEspaciosPorMiembroQuery.Companion.operationName,
    ObtenerEspaciosPorMiembroQuery.Companion.dataDeserializer,
    ObtenerEspaciosPorMiembroQuery.Companion.variablesSerializer,
  )


private class ObtenerEstadisticasArchivoQueryImpl(
  connector: DefaultConnector
):
  ObtenerEstadisticasArchivoQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerEstadisticasArchivoQuery.Data,
      ObtenerEstadisticasArchivoQuery.Variables
  >(
    connector,
    ObtenerEstadisticasArchivoQuery.Companion.operationName,
    ObtenerEstadisticasArchivoQuery.Companion.dataDeserializer,
    ObtenerEstadisticasArchivoQuery.Companion.variablesSerializer,
  )


private class ObtenerEstructuraLimpiezaArchivoQueryImpl(
  connector: DefaultConnector
):
  ObtenerEstructuraLimpiezaArchivoQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerEstructuraLimpiezaArchivoQuery.Data,
      ObtenerEstructuraLimpiezaArchivoQuery.Variables
  >(
    connector,
    ObtenerEstructuraLimpiezaArchivoQuery.Companion.operationName,
    ObtenerEstructuraLimpiezaArchivoQuery.Companion.dataDeserializer,
    ObtenerEstructuraLimpiezaArchivoQuery.Companion.variablesSerializer,
  )


private class ObtenerInsigniasNotificadasQueryImpl(
  connector: DefaultConnector
):
  ObtenerInsigniasNotificadasQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerInsigniasNotificadasQuery.Data,
      ObtenerInsigniasNotificadasQuery.Variables
  >(
    connector,
    ObtenerInsigniasNotificadasQuery.Companion.operationName,
    ObtenerInsigniasNotificadasQuery.Companion.dataDeserializer,
    ObtenerInsigniasNotificadasQuery.Companion.variablesSerializer,
  )


private class ObtenerLeaderboardQueryImpl(
  connector: DefaultConnector
):
  ObtenerLeaderboardQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerLeaderboardQuery.Data,
      Unit
  >(
    connector,
    ObtenerLeaderboardQuery.Companion.operationName,
    ObtenerLeaderboardQuery.Companion.dataDeserializer,
    ObtenerLeaderboardQuery.Companion.variablesSerializer,
  )


private class ObtenerMisEspaciosQueryImpl(
  connector: DefaultConnector
):
  ObtenerMisEspaciosQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerMisEspaciosQuery.Data,
      ObtenerMisEspaciosQuery.Variables
  >(
    connector,
    ObtenerMisEspaciosQuery.Companion.operationName,
    ObtenerMisEspaciosQuery.Companion.dataDeserializer,
    ObtenerMisEspaciosQuery.Companion.variablesSerializer,
  )


private class ObtenerPanelSolicitudesQueryImpl(
  connector: DefaultConnector
):
  ObtenerPanelSolicitudesQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerPanelSolicitudesQuery.Data,
      Unit
  >(
    connector,
    ObtenerPanelSolicitudesQuery.Companion.operationName,
    ObtenerPanelSolicitudesQuery.Companion.dataDeserializer,
    ObtenerPanelSolicitudesQuery.Companion.variablesSerializer,
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


private class ObtenerRankingUsuarioQueryImpl(
  connector: DefaultConnector
):
  ObtenerRankingUsuarioQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerRankingUsuarioQuery.Data,
      ObtenerRankingUsuarioQuery.Variables
  >(
    connector,
    ObtenerRankingUsuarioQuery.Companion.operationName,
    ObtenerRankingUsuarioQuery.Companion.dataDeserializer,
    ObtenerRankingUsuarioQuery.Companion.variablesSerializer,
  )


private class ObtenerUsuarioPorCredencialesQueryImpl(
  connector: DefaultConnector
):
  ObtenerUsuarioPorCredencialesQuery,
  DefaultConnectorGeneratedQueryImpl<
      ObtenerUsuarioPorCredencialesQuery.Data,
      ObtenerUsuarioPorCredencialesQuery.Variables
  >(
    connector,
    ObtenerUsuarioPorCredencialesQuery.Companion.operationName,
    ObtenerUsuarioPorCredencialesQuery.Companion.dataDeserializer,
    ObtenerUsuarioPorCredencialesQuery.Companion.variablesSerializer,
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


private class RegistrarIntentoExamenMutationImpl(
  connector: DefaultConnector
):
  RegistrarIntentoExamenMutation,
  DefaultConnectorGeneratedMutationImpl<
      RegistrarIntentoExamenMutation.Data,
      RegistrarIntentoExamenMutation.Variables
  >(
    connector,
    RegistrarIntentoExamenMutation.Companion.operationName,
    RegistrarIntentoExamenMutation.Companion.dataDeserializer,
    RegistrarIntentoExamenMutation.Companion.variablesSerializer,
  )


private class RegistrarLogroNotificadoMutationImpl(
  connector: DefaultConnector
):
  RegistrarLogroNotificadoMutation,
  DefaultConnectorGeneratedMutationImpl<
      RegistrarLogroNotificadoMutation.Data,
      RegistrarLogroNotificadoMutation.Variables
  >(
    connector,
    RegistrarLogroNotificadoMutation.Companion.operationName,
    RegistrarLogroNotificadoMutation.Companion.dataDeserializer,
    RegistrarLogroNotificadoMutation.Companion.variablesSerializer,
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


private class SeedRolesDataMutationImpl(
  connector: DefaultConnector
):
  SeedRolesDataMutation,
  DefaultConnectorGeneratedMutationImpl<
      SeedRolesDataMutation.Data,
      Unit
  >(
    connector,
    SeedRolesDataMutation.Companion.operationName,
    SeedRolesDataMutation.Companion.dataDeserializer,
    SeedRolesDataMutation.Companion.variablesSerializer,
  )


private class UnirseAEspacioMutationImpl(
  connector: DefaultConnector
):
  UnirseAEspacioMutation,
  DefaultConnectorGeneratedMutationImpl<
      UnirseAEspacioMutation.Data,
      UnirseAEspacioMutation.Variables
  >(
    connector,
    UnirseAEspacioMutation.Companion.operationName,
    UnirseAEspacioMutation.Companion.dataDeserializer,
    UnirseAEspacioMutation.Companion.variablesSerializer,
  )


