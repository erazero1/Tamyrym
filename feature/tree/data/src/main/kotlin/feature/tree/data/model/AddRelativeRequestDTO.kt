package feature.tree.data.model

import feature.tree.domain.model.AddRelativeRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRelativeRequestDTO(
    @SerialName("person")
    val person: PersonRequestDTO?,
    @SerialName("relation")
    val relation: String?,
    @SerialName("relative_id")
    val relativeId: String?
)

fun AddRelativeRequest.toDTO(): AddRelativeRequestDTO {
    return AddRelativeRequestDTO(
        person = person.toDTO(),
        relation = relation.value,
        relativeId = relativeId
    )
}