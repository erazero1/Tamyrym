package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LoginRequestDTO(
    @SerializedName("email")
    val email: String,
    @SerializedName("password")
    val password: String,
)
