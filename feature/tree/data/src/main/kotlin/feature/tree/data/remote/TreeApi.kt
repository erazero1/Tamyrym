package feature.tree.data.remote

import core.domain.result.ApiResult
import feature.tree.data.model.AddRelationRequestDTO
import feature.tree.data.model.PersonDTO
import feature.tree.data.model.TreeDTO
import feature.tree.data.model.TreeGraphDTO
import feature.tree.data.model.TreeListDTO
import feature.tree.data.model.TreeRequestDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TreeApi {
    @POST("api/v1/genealogy/tree") // OK
    suspend fun createTree(@Body body: TreeRequestDTO): ApiResult<TreeDTO>

    @GET("api/v1/genealogy/tree/graph")
    suspend fun getTreeGraph(
        @Query("tree_id") treeId: String,
        @Query("target_person_id") targetPersonId: String,
        @Query("depth") depth: Int,
        @Query("preview") preview: Boolean,
    ): ApiResult<TreeGraphDTO>

    @GET("api/v1/genealogy/tree/list")
    suspend fun getTreeList(): ApiResult<TreeListDTO>

    @DELETE("api/v1/genealogy/tree/{treeId}")
    suspend fun deleteTree(@Path("treeId") treeId: String): ApiResult<Unit>

    @PATCH("api/v1/genealogy/tree/{treeId}")
    suspend fun updateTree(
        @Path("treeId") treeId: String,
        @Body body: TreeRequestDTO,
    ): ApiResult<TreeDTO>

    @GET("api/v1/genealogy/tree/{treeId}/export/gedcom")
    suspend fun exportGedcom(@Path("treeId") treeId: String): ApiResult<Unit>

    @POST("api/v1/genealogy/tree/{treeId}/import/gedcom")
    suspend fun importGedcom(@Path("treeId") treeId: String): ApiResult<Unit>

    @POST("api/v1/genealogy/tree/{tree_id}/person")
    suspend fun addRelationToPerson(
        @Path("tree_id") treeId: String,
        @Body body: AddRelationRequestDTO,
    ): ApiResult<PersonDTO>
}