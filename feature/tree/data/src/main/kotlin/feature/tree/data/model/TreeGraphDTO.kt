package feature.tree.data.model

import feature.tree.domain.model.TreeGraph
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TreeGraphDTO(
    @SerialName("links")
    val links: List<LinkDTO?>?,
    @SerialName("persons")
    val persons: List<PersonExtDTO?>?,
    @SerialName("unions")
    val unions: List<UnionDTO?>?,
)

fun TreeGraphDTO.toDomain(): TreeGraph {
    return TreeGraph(
        links = this.links?.filterNotNull()?.map { it.toDomain() } ?: emptyList(),
        persons = this.persons?.filterNotNull()?.map { it.toDomain() } ?: emptyList(),
        unions = this.unions?.filterNotNull()?.map { it.toDomain() } ?: emptyList()
    )
}