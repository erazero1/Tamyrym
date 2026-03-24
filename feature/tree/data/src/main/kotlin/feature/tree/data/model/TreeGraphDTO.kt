package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.TreeGraph
import kotlinx.serialization.Serializable

@Serializable
data class TreeGraphDTO(
    @SerializedName("persons")
    val persons: List<PersonInfoDTO>? = null,

    @SerializedName("root_person_ids")
    val rootPersonIds: List<String>? = null,

    @SerializedName("slice")
    val slice: SliceDTO? = null,

    @SerializedName("unions")
    val unions: List<UnionDTO>? = null,
)

fun TreeGraphDTO.toDomain(): TreeGraph {
    return TreeGraph(
        persons = this.persons?.map { it.toDomain() } ?: emptyList(),
        rootPersonIds = this.rootPersonIds ?: emptyList(),
        slice = this.slice?.toDomain(),
        unions = this.unions?.map { it.toDomain() } ?: emptyList()
    )
}