package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.repository.PersonRepository

class DeletePersonUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke(personId: String): ApiResult<Unit> {
        return repository.deletePerson(personId = personId)
    }
}