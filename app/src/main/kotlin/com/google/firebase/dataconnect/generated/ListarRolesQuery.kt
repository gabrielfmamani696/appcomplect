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

public interface ListarRolesQuery :
    GeneratedQuery<
        DefaultConnector,
        ListarRolesQuery.Data,
        Unit
    >
{

    @Serializable
    public data class Data(
        val rolUsuarios: List<RolUsuariosItem>
    ) {

        @Serializable
        public data class RolUsuariosItem(
            val id: @Serializable(with = UUIDSerializer::class) UUID,
            val nombreRol: String
        ) {
        }
    }

    public companion object {
        public val operationName: String = "ListarRoles"

        public val dataDeserializer: DeserializationStrategy<Data> =
            serializer()

        public val variablesSerializer: SerializationStrategy<Unit> =
            serializer()
    }
}

public fun ListarRolesQuery.ref(
): QueryRef<
        ListarRolesQuery.Data,
        Unit
        > =
    ref(

        Unit

    )

public suspend fun ListarRolesQuery.execute(

): QueryResult<
        ListarRolesQuery.Data,
        Unit
        > =
    ref(

    ).execute()

public fun ListarRolesQuery.flow(

): Flow<ListarRolesQuery.Data> =
    ref(

    ).subscribe()
        .flow
        ._flow_map { querySubscriptionResult -> querySubscriptionResult.result.getOrNull() }
        ._flow_filterNotNull()
        ._flow_map { it.data }
