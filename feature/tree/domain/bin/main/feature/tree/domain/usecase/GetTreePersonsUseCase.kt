package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.Person
import feature.tree.domain.repository.PersonRepository

class GetTreePersonsUseCase(private val repository: PersonRepository) {
    suspend operator fun invoke(treeId: String): ApiResult<List<Person>> {
        return repository.getTreePersons(treeId = treeId)
    }
}