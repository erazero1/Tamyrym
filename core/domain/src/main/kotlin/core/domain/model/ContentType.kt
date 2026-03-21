package core.domain.model

enum class ContentType(val type: String) {
    JPEG("image/jpeg"),
    PNG("image/png"),
    MP3("audio/mpeg"),
    MP4("video/mp4")
}