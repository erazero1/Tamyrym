package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import feature.tree.domain.model.PersonRequest
import kotlinx.serialization.Serializable

@Serializable
data class PersonRequestDTO(
    @SerializedName("biography")
    val biography: String?,
    @SerializedName("birth_date")
    val birthDate: String?,
    @SerializedName("birth_place")
    val birthPlace: String?,
    @SerializedName("death_date")
    val deathDate: String?,
    @SerializedName("death_place")
    val deathPlace: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("is_alive")
    val isAlive: Boolean?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("maiden_name")
    val maidenName: String?,
    @SerializedName("patronymic")
    val patronymic: String?,
    @SerializedName("photo_url")
    val photoUrl: String?,
)

fun PersonRequest.toDTO(): PersonRequestDTO {
    return PersonRequestDTO(
        biography = biography,
        birthDate = birthDate.ifEmpty { null },
        birthPlace = birthPlace,
        deathDate = deathDate.ifEmpty { null },
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