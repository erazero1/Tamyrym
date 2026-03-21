package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutRequestDTO(
    @SerializedName("refresh_token") val refreshToken: String,
)