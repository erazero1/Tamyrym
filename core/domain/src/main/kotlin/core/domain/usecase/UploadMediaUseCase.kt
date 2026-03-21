package core.domain.usecase

import core.domain.model.ContentType
import core.domain.model.UploadUrlResponse
import core.domain.repository.MediaUploadRepository
import core.domain.result.ApiResult
import java.io.File

class UploadMediaUseCase(private val repository: MediaUploadRepository) {
    suspend operator fun invoke(
        uploadDetails: UploadUrlResponse,
        mediaFile: File,
        contentType: ContentType,
    ): ApiResult<Unit> {
        return repository.uploadMedia(
            uploadDetails = uploadDetails,
            mediaFile = mediaFile,
            contentType = contentType,
        )
    }
}