package core.domain.usecase

import core.domain.model.UploadUrlResponse
import core.domain.repository.MediaRepository
import core.domain.result.ApiResult

class GetPersonMediaUploadUrlUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(mediaId: String): ApiResult<UploadUrlResponse> {
        return repository.getPersonMediaUploadUrl(mediaId = mediaId)
    }
}