package feature.tree.data.model

import feature.tree.domain.model.Tree
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TreeListDTO(
    @SerialName("trees")
    val trees: List<TreeDTO?>?,
)

fun TreeListDTO.toDomain(): List<Tree> {
    return trees?.filterNotNull()?.map { it.toDomain() } ?: emptyList()
}