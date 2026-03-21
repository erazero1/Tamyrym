package core.data.common.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LogoutResponseDTO(
    @SerialName("message")
    val message: String?,
)