package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GoogleOAuthRequestDTO(
    @SerializedName("id_token")
    val idToken: String,
)
