package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleOAuthResponseDTO(
    @SerializedName("tokens")
    val tokens: Token,
    @SerializedName("user_id")
    val userId: String,
)
