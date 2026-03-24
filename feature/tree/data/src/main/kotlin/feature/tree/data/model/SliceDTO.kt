package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.Slice
import kotlinx.serialization.Serializable

@Serializable
data class SliceDTO(
    @SerializedName("depth")
    val depth: Int? = null,

    @SerializedName("focus_person_id")
    val focusPersonId: String? = null,

    @SerializedName("truncated")
    val truncated: Boolean? = null,
)

fun SliceDTO.toDomain(): Slice {
    return Slice(
        depth = this.depth ?: 0,
        focusPersonId = this.focusPersonId.orEmpty(),
        truncated = this.truncated ?: false
    )
}
