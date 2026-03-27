package core.domain.model

data class GetUploadUrlRequest(
    val contentType: ContentType,
    val filename: String,
)
