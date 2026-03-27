package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.Person
import feature.tree.domain.repository.PersonRepository

class GetPersonsByTreeId(private val repository: PersonRepository) {
    suspend operator fun invoke(
        treeId: String,
        page: Int,
        searchQuery: String = "",
        sortByField: String = "",
        limit: Int = 0,
        offset: Int = 0,
        pageSize: Int = 20,
    ): ApiResult<List<Person>> {
        return repository.getPersonsByTreeId(
            treeId = treeId,
            page = page,
            searchQuery = searchQuery,
            sortByField = sortByField,
            limit = limit,
            offset = offset,
            pageSize = pageSize,
        )
    }
}