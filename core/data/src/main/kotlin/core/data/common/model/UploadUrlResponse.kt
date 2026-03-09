package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class UploadUrlResponse(
    @SerializedName("url")
    val url: String,
    @SerializedName("method")
    val method: String,
    @SerializedName("object_key")
    val objectKey: String,
)
