package feature.tree.data.model

import com.google.gson.annotations.SerializedName
import core.domain.model.Gender
import feature.tree.domain.model.PersonInfo
import kotlinx.serialization.Serializable

@Serializable
data class PersonInfoDTO(
    @SerializedName("first_name")
    val firstName: String? = null,

    @SerializedName("gender")
    val gender: String? = null,

    @SerializedName("generation")
    val generation: Int? = null,

    @SerializedName("id")
    val id: String? = null,

    @SerializedName("last_name")
    val lastName: String? = null,

    @SerializedName("main_union_id")
    val mainUnionId: String? = null,
)

fun PersonInfoDTO.toDomain(): PersonInfo {
    return PersonInfo(
        id = this.id.orEmpty(),
        firstName = this.firstName.orEmpty(),
        lastName = this.lastName.orEmpty(),
        gender = Gender.findValue(this.gender),
        generation = this.generation ?: 0,
        mainUnionId = this.mainUnionId.orEmpty()
    )
}