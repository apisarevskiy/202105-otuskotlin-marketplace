package ru.otus.otuskotlin.marketplace.backend.repo.cassandra

import com.datastax.oss.driver.api.core.type.DataTypes
import com.datastax.oss.driver.api.mapper.annotations.CqlName
import com.datastax.oss.driver.api.mapper.annotations.Entity
import com.datastax.oss.driver.api.mapper.annotations.PartitionKey
import com.datastax.oss.driver.api.querybuilder.SchemaBuilder
import ru.otus.otuskotlin.marketplace.backend.common.models.*

@Entity
data class AdCassandraDTO(
    @field:CqlName(COLUMN_ID)
    @field:PartitionKey
    var id: String? = null,
    @field:CqlName(COLUMN_TITLE)
    var title: String? = null,
    @field:CqlName(COLUMN_DESCRIPTION)
    var description: String? = null,
    @field:CqlName(COLUMN_OWNER_ID)
    var ownerId: String? = null,
    @field:CqlName(COLUMN_VISIBILITY)
    var visibility: AdVisibilityModel? = null,
    @field:CqlName(COLUMN_DEAL_SIDE)

    // Нельзя использовать в моделях хранения внутренние модели.
    // При изменении внутренних моделей, БД автоматически не изменится,
    // а потому будет Runtime ошибка, которая вылезет только на продуктовом стенде
    var dealSide: DealSideModel? = null,
    @field:CqlName(COLUMN_PERMISSIONS)
    var permissions: Set<PermissionModel>? = null
) {
    constructor(adModel: AdModel) : this(
        ownerId = adModel.ownerId.takeIf { it != OwnerIdModel.NONE }?.asString(),
        id = adModel.id.takeIf { it != AdIdModel.NONE }?.asString(),
        title = adModel.title.takeIf { it.isNotBlank() },
        description = adModel.description.takeIf { it.isNotBlank() },
        visibility = adModel.visibility.takeIf { it != AdVisibilityModel.NONE },
        dealSide = adModel.dealSide.takeIf { it != DealSideModel.NONE },
        permissions = adModel.permissions.takeIf { it.isNotEmpty() }
    )

    fun toAdModel(): AdModel =
        AdModel(
            ownerId = ownerId?.let { OwnerIdModel(it) } ?: OwnerIdModel.NONE,
            id = id?.let { AdIdModel(it) } ?: AdIdModel.NONE,
            title = title ?: "",
            description = description ?: "",
            visibility = visibility ?: AdVisibilityModel.NONE,
            dealSide = dealSide ?: DealSideModel.NONE,
            permissions = permissions.orEmpty().toMutableSet()
        )

    companion object {
        const val TABLE_NAME = "ads"

        const val COLUMN_ID = "id"
        const val COLUMN_TITLE = "title"
        const val COLUMN_DESCRIPTION = "description"
        const val COLUMN_OWNER_ID = "\"owner_id_my\""
        const val COLUMN_VISIBILITY = "visibility"
        const val COLUMN_DEAL_SIDE = "deal_side"
        const val COLUMN_PERMISSIONS = "permissions"

        fun table(keyspace: String, tableName: String) =
            SchemaBuilder
                .createTable(keyspace, tableName)
                .ifNotExists()
                .withPartitionKey(COLUMN_ID, DataTypes.TEXT)
                .withColumn(COLUMN_TITLE, DataTypes.TEXT)
                .withColumn(COLUMN_DESCRIPTION, DataTypes.TEXT)
                .withColumn(COLUMN_OWNER_ID, DataTypes.TEXT)
                .withColumn(COLUMN_VISIBILITY, DataTypes.TEXT)
                .withColumn(COLUMN_DEAL_SIDE, DataTypes.TEXT)
                .withColumn(COLUMN_PERMISSIONS, DataTypes.setOf(DataTypes.TEXT))
                .build()

        fun titleIndex(keyspace: String, tableName: String, locale: String = "en") =
            SchemaBuilder
                .createIndex()
                .ifNotExists()
                .usingSASI()
                .onTable(keyspace, tableName)
                .andColumn(COLUMN_TITLE)
                .withSASIOptions(mapOf("mode" to "CONTAINS", "tokenization_locale" to locale))
                .build()
    }
}
