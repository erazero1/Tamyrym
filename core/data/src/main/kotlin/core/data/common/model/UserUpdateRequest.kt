package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UserUpdateRequest(
    @SerializedName("birth_year")
    val birthYear: Int,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("gender")
    val gender: String,
    @SerializedName("last_name")
    val lastName: String,
    @SerializedName("picture_url")
    val pictureUrl: String,
)
