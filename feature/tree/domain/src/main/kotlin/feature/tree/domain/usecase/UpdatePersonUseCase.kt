package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.Person
import feature.tree.domain.model.PersonRequest
import feature.tree.domain.repository.PersonRepository

class UpdatePersonUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke(personId: String, personRequest: PersonRequest): ApiResult<Person> {
        return repository.updatePerson(
            personId = personId,
            body = personRequest,
        )
    }
}