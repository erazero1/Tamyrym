package feature.tree.data.model

import com.erazero1.utils.toInstantOrNull
import com.google.gson.annotations.SerializedName
import core.domain.model.Gender
import feature.tree.domain.model.PersonExt
import kotlinx.serialization.Serializable
import java.time.Instant

@Serializable
data class PersonExtDTO(
    @SerializedName("biography")
    val biography: String?,
    @SerializedName("birth_date")
    val birthDate: String?,
    @SerializedName("birth_date_string")
    val birthDateString: String?,
    @SerializedName("birth_place")
    val birthPlace: String?,
    @SerializedName("created_at")
    val createdAt: String?,
    @SerializedName("created_by")
    val createdBy: String?,
    @SerializedName("death_date")
    val deathDate: String?,
    @SerializedName("death_date_string")
    val deathDateString: String?,
    @SerializedName("death_place")
    val deathPlace: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("gedcom_id")
    val gedcomId: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: String?,
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
    @SerializedName("tree_id")
    val treeId: String?,
    @SerializedName("updated_at")
    val updatedAt: String?,
)

fun PersonExtDTO.toDomain(): PersonExt {
    return PersonExt(
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
        gedcomId = this.gedcomId.orEmpty(),
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