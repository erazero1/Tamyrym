package core.domain.model

data class UploadUrlResponse(
    val url: String,
    val method: String,
    val objectKey: String,
)