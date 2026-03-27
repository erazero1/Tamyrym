package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.AddRelationRequest
import kotlinx.serialization.Serializable

@Serializable
data class AddRelationRequestDTO(
    @SerializedName("person")
    val person: PersonRequestDTO?,
    @SerializedName("person_id_to_focus")
    val personIdToFocus: String?,
    @SerializedName("relation_type")
    val relationType: String?,
)

fun AddRelationRequest.toDTO(): AddRelationRequestDTO {
    return AddRelationRequestDTO(
        person = person.toDTO(),
        relationType = relationType?.value,
        personIdToFocus = personIdToFocus
    )
}