package feature.tree.data.model

import com.erazero1.utils.toInstantOrNull
import feature.tree.domain.model.Tree
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class TreeDTO(
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("id")
    val id: String?,
    @SerialName("name")
    val name: String?,
    @SerialName("owner_id")
    val ownerId: String?,
    @SerialName("updated_at")
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