package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RefreshAccessTokenRequest(
    @SerializedName("refresh_token")
    val refreshToken: String,
)