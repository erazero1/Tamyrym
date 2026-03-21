package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class GetUploadUrlRequestDTO(
    @SerializedName("content_type")
    val contentType: String,
    @SerializedName("filename")
    val filename: String,
)
