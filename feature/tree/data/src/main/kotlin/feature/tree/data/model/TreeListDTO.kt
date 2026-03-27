package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.Tree
import kotlinx.serialization.Serializable

@Serializable
data class TreeListDTO(
    @SerializedName("trees")
    val trees: List<TreeDTO?>?,
)

fun TreeListDTO.toDomain(): List<Tree> {
    return trees?.filterNotNull()?.map { it.toDomain() } ?: emptyList()
}