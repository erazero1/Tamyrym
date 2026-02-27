package feature.tree.data.model

import com.erazero1.utils.toInstantOrNull
import core.domain.model.Gender
import feature.tree.domain.model.Person
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class PersonDTO(
    @SerialName("biography")
    val biography: String?,
    @SerialName("birth_date")
    val birthDate: String?,
    @SerialName("birth_date_string")
    val birthDateString: String?,
    @SerialName("birth_place")
    val birthPlace: String?,
    @SerialName("created_at")
    val createdAt: String?,
    @SerialName("created_by")
    val createdBy: String?,
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
    @SerialName("id")
    val id: String?,
    @SerialName("is_alive")
    val isAlive: Boolean?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("maiden_name")
    val maidenName: String?,
    @SerialName("patronymic")
    val patronymic: String?,
    @SerialName("photo_url")
    val photoUrl: String?,
    @SerialName("tree_id")
    val treeId: String?,
    @SerialName("updated_at")
    val updatedAt: String?
)

fun PersonDTO.toDomain(): Person {
    return Person(
        biography = this.biography.orEmpty(),
        birthDate = this.birthDate.toInstantOrNull() ?: Instant.now(),
        birthDateString = this.birthDateString.orEmpty(),
        birthPlace = this.birthPlace.orEmpty(),
        createdAt = this.createdAt.toInstantOrNull() ?: Instant.now(),
        createdBy = this.createdBy.orEmpty(),
        deathDate = this.deathDate.toInstantOrNull() ?: Instant.now(),
        deathDateString = this.deathDateString.orEmpty(),
        deathPlace = this.deathPlace.orEmpty(),
        firstName = this.firstName.orEmpty(),
        gender = Gender.findValue(this.gender),
        id = this.id.orEmpty(),
        isAlive = this.isAlive ?: true,
        lastName = this.lastName.orEmpty(),
        maidenName = this.maidenName.orEmpty(),
        patronymic = this.patronymic.orEmpty(),
        photoUrl = this.photoUrl.orEmpty(),
        treeId = this.treeId.orEmpty(),
        updatedAt = this.updatedAt.toInstantOrNull() ?: Instant.now()
    )
}