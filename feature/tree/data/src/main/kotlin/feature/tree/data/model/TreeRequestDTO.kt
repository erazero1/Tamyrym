package feature.tree.data.model

import feature.tree.domain.model.TreeRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TreeRequestDTO(
    @SerialName("name")
    val name: String,
    @SerialName("description")
    val description: String
)
fun TreeRequest.toDTO(): TreeRequestDTO {
    return TreeRequestDTO(
        name = this.name,
        description = this.description
    )
}