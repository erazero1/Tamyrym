package core.data.common.model

import com.google.gson.annotations.SerializedName
import core.domain.model.UploadUrlResponse
import kotlinx.serialization.Serializable

@Serializable
data class UploadUrlResponseDTO(
    @SerializedName("url")
    val url: String?,
    @SerializedName("method")
    val method: String?,
    @SerializedName("object_key")
    val objectKey: String?,
)

fun UploadUrlResponseDTO.toDomain(): UploadUrlResponse {
    return UploadUrlResponse(
        url = this.url.orEmpty(),
        method = this.method.orEmpty(),
        objectKey = this.objectKey.orEmpty(),
    )
}