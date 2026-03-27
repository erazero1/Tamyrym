package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.Person
import feature.tree.domain.repository.PersonRepository

class GetPersonUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke(personId: String): ApiResult<Person> {
        return repository.getPerson(personId = personId)
    }
}