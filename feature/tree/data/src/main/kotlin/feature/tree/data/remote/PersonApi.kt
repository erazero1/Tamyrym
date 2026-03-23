package feature.tree.data.remote

import core.domain.result.ApiResult
import feature.tree.data.model.PersonDTO
import feature.tree.data.model.PersonRequestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

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

    @GET("api/v1/genealogy/tree/{treeId}/person")
    suspend fun getTreePersons(@Path("treeId") treeId: String): ApiResult<List<PersonDTO?>?>

    @GET("api/v1/genealogy/person")
    suspend fun getPersonsByTreeId(
        @Query("tree_id") treeId: String,
        @Query("search") searchQuery: String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("page") page: Int,
        @Query("page_size") pageSize: Int,
        @Query("sort") sortByField: String,
    ): ApiResult<List<PersonDTO?>?>
}