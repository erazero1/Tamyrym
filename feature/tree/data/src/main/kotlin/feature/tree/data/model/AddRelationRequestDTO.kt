package feature.tree.data.model

import feature.tree.domain.model.AddRelationRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AddRelationRequestDTO(
    @SerialName("person")
    val person: PersonRequestDTO?,
    @SerialName("person_id_to_focus")
    val personIdToFocus: String?,
    @SerialName("relation_type")
    val relationType: String?,
)

fun AddRelationRequest.toDTO(): AddRelationRequestDTO {
    return AddRelationRequestDTO(
        person = person.toDTO(),
        relationType = relationType.value,
        personIdToFocus = personIdToFocus
    )
}