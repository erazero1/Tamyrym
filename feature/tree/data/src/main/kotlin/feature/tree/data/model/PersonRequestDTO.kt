package feature.tree.data.model

import feature.tree.domain.model.PersonRequest
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PersonRequestDTO(
    @SerialName("biography")
    val biography: String?,
    @SerialName("birth_date")
    val birthDate: String?,
    @SerialName("birth_date_string")
    val birthDateString: String?,
    @SerialName("birth_place")
    val birthPlace: String?,
    @SerialName("death_date")
    val deathDate: String?,
    @SerialName("death_date_string")
    val deathDateString: String?,
    @SerialName("death_place")
    val deathPlace: String?,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("gender")
    val gender: String?,
    @SerialName("is_alive")
    val isAlive: Boolean?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("maiden_name")
    val maidenName: String?,
    @SerialName("patronymic")
    val patronymic: String?,
    @SerialName("photo_url")
    val photoUrl: String?
)

fun PersonRequest.toDTO(): PersonRequestDTO {
    return PersonRequestDTO(
        biography = biography,
        birthDate = birthDate.toString(),
        birthDateString = birthDateString,
        birthPlace = birthPlace,
        deathDate = deathDate.toString(),
        deathDateString = deathDateString,
        deathPlace = deathPlace,
        firstName = firstName,
        gender = gender.value,
        isAlive = isAlive,
        lastName = lastName,
        maidenName = maidenName,
        patronymic = patronymic,
        photoUrl = photoUrl
    )
}