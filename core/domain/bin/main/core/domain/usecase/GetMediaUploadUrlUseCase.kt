package core.domain.usecase

import core.domain.model.GetUploadUrlRequest
import core.domain.model.UploadUrlResponse
import core.domain.repository.MediaRepository
import core.domain.result.ApiResult

class GetMediaUploadUrlUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(getUploadUrlRequest: GetUploadUrlRequest): ApiResult<UploadUrlResponse> {
        return repository.getMediaUploadUrl(getUploadUrlRequest = getUploadUrlRequest)
    }
}