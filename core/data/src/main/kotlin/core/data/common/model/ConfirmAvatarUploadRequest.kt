package core.data.common.model

import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class ConfirmAvatarUploadRequest(
    @SerializedName("object_key")
    val objectKey: String,
)
