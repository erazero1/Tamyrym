package core.domain.usecase

import core.domain.repository.MediaRepository
import core.domain.result.ApiResult

class ConfirmMediaUploadUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(objectKey: String): ApiResult<Unit> {
        return repository.confirmUpload(objectKey = objectKey)
    }
}