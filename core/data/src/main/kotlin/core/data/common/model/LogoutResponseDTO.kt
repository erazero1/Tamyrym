package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponseDTO(
    @SerializedName("message")
    val message: String?,
)