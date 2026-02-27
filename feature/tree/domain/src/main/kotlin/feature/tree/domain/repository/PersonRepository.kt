package feature.tree.domain.repository

import core.domain.result.ApiResult
import feature.tree.domain.model.AddRelativeRequest
import feature.tree.domain.model.Person
import feature.tree.domain.model.PersonRequest

interface PersonRepository {
    suspend fun getPerson(personId: String): ApiResult<Person>
    suspend fun deletePerson(personId: String): ApiResult<Unit>
    suspend fun updatePerson(personId: String, body: PersonRequest): ApiResult<Person>
    suspend fun addRelation(personId: String, body: AddRelativeRequest): ApiResult<Person>
    suspend fun getTreePersons(treeId: String): ApiResult<List<Person>>
}