package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.AddRelativeRequest
import feature.tree.domain.model.Person
import feature.tree.domain.repository.PersonRepository

class AddRelationUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke(
        personId: String,
        addRelativeRequest: AddRelativeRequest,
    ): ApiResult<Person> {
        return repository.addRelation(
            personId = personId,
            body = addRelativeRequest,
        )
    }
}