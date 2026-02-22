package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterResponse(
    @SerializedName("tokens")
    val token: Token,
    @SerializedName("user_id")
    val userId: String,
)
