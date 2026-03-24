package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import core.domain.model.UnionType
import feature.tree.domain.model.Union
import kotlinx.serialization.Serializable

@Serializable
data class UnionDTO(
    @SerializedName("children_ids")
    val childrenIds: List<String>? = null,

    @SerializedName("generation")
    val generation: Int? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("is_virtual")
    val isVirtual: Boolean? = null,

    @SerializedName("person_1_id")
    val person1Id: String? = null,

    @SerializedName("person_2_id")
    val person2Id: String? = null,

    @SerializedName("type")
    val type: String? = null,
)

fun UnionDTO.toDomain(): Union {
    return Union(
        id = this.id.orEmpty(),
        type = UnionType.findValue(this.type),
        generation = this.generation ?: 0,
        isVirtual = this.isVirtual ?: false,
        person1Id = this.person1Id.orEmpty(),
        person2Id = this.person2Id.orEmpty(),
        childrenIds = this.childrenIds ?: emptyList()
    )
}