package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.TreeRequest
import kotlinx.serialization.Serializable

@Serializable
data class TreeRequestDTO(
    @SerializedName("name")
    val name: String,
    @SerializedName("description")
    val description: String,
)
fun TreeRequest.toDTO(): TreeRequestDTO {
    return TreeRequestDTO(
        name = this.name,
        description = this.description
    )
}