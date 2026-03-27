package feature.tree.data.model

import com.erazero1.utils.toInstantOrNull
import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.Tree
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TreeDTO(
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("owner_id")
    val ownerId: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
)

fun TreeDTO.toDomain(): Tree {
    return Tree(
        createdAt = this.createdAt.toInstantOrNull() ?: Instant.now(),
        description = this.description.orEmpty(),
        id = this.id.orEmpty(),
        name = this.name.orEmpty(),
        ownerId = this.ownerId.orEmpty(),
        updatedAt = this.updatedAt.toInstantOrNull() ?: Instant.now()
    )
}