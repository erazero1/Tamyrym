package feature.tree.data.remote

import core.domain.result.ApiResult
import feature.tree.data.model.AddRelativeRequestDTO
import feature.tree.data.model.PersonDTO
import feature.tree.data.model.PersonRequestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path

interface PersonApi {
    @GET("api/v1/genealogy/person/{personId}")
    suspend fun getPerson(@Path("personId") personId: String): ApiResult<PersonDTO>

    @DELETE("api/v1/genealogy/person/{personId}")
    suspend fun deletePerson(
        @Path("personId") personId: String,
    ): ApiResult<Unit>

    @PATCH("api/v1/genealogy/person/{personId}")
    suspend fun updatePerson(
        @Path("personId") personId: String,
        @Body body: PersonRequestDTO,
    ): ApiResult<PersonDTO>

    @POST("api/v1/genealogy/person/{personId}/relation")
    suspend fun addRelation(
        @Path("personId") personId: String,
        @Body body: AddRelativeRequestDTO,
    ): ApiResult<PersonDTO>

    @GET("api/v1/genealogy/tree/{treeId}/person")
    suspend fun getTreePersons(@Path("treeId") treeId: String): ApiResult<List<PersonDTO?>?>
}