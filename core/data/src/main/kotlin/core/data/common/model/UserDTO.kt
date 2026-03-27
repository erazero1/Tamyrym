package core.data.common.model

import com.google.gson.annotations.SerializedName

data class UserDTO(
    @SerializedName("birth_year")
    val birthYear: Int?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("first_name")
    val firstName: String?,
    @SerializedName("gender")
    val gender: String?,
    @SerializedName("id")
    val id: String?,
    @SerializedName("last_name")
    val lastName: String?,
    @SerializedName("role")
    val role: String?,
    @SerializedName("status")
    val userStatus: String?,
    @SerializedName("attrs")
    val attrs: Map<String, String>?,
)
