package feature.tree.data.repository

import core.domain.result.ApiResult
import core.domain.result.map
import feature.tree.data.model.toDTO
import feature.tree.data.model.toDomain
import feature.tree.data.remote.PersonApi
import feature.tree.domain.model.AddRelativeRequest
import feature.tree.domain.model.Person
import feature.tree.domain.model.PersonRequest
import feature.tree.domain.repository.PersonRepository

class PersonRepositoryImpl(
    private val personApi: PersonApi,
) : PersonRepository {
    override suspend fun getPerson(personId: String): ApiResult<Person> {
        return personApi.getPerson(personId = personId).map { it.toDomain() }
    }

    override suspend fun deletePerson(personId: String): ApiResult<Unit> {
        return personApi.deletePerson(personId = personId)
    }

    override suspend fun updatePerson(personId: String, body: PersonRequest): ApiResult<Person> {
        return personApi.updatePerson(personId = personId, body = body.toDTO())
            .map { it.toDomain() }
    }

    override suspend fun addRelation(
        personId: String,
        body: AddRelativeRequest,
    ): ApiResult<Person> {
        return personApi.addRelation(
            personId = personId,
            body = body.toDTO(),
        ).map { it.toDomain() }
    }

    override suspend fun getTreePersons(treeId: String): ApiResult<List<Person>> {
        return personApi.getTreePersons(treeId = treeId)
            .map { persons -> persons?.filterNotNull()?.map { it.toDomain() } ?: emptyList() }
    }
}