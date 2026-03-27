package core.domain.usecase

import core.domain.repository.MediaRepository
import core.domain.result.ApiResult

class GetPersonMediaUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(personId: String): ApiResult<Unit> {
        return repository.getPersonMedia(personId = personId)
    }
}