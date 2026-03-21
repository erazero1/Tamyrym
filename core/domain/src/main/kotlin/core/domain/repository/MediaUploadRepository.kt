package core.domain.repository

import core.domain.model.ContentType
import core.domain.model.UploadUrlResponse
import core.domain.result.ApiResult
import java.io.File

interface MediaUploadRepository {
    suspend fun uploadMedia(
        uploadDetails: UploadUrlResponse,
        mediaFile: File,
        contentType: ContentType,
    ): ApiResult<Unit>
}