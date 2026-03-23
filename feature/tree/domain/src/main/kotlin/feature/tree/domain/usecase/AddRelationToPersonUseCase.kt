package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.AddRelationRequest
import feature.tree.domain.model.Person
import feature.tree.domain.repository.TreeRepository

class AddRelationToPersonUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(treeId: String, body: AddRelationRequest): ApiResult<Person> {
        return repository.addRelationToPerson(
            treeId = treeId,
            body = body,
        )
    }
}