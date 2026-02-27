package feature.tree.data.remote

import core.domain.result.ApiResult
import feature.tree.data.model.TreeRequestDTO
import feature.tree.data.model.TreeDTO
import feature.tree.data.model.TreeGraphDTO
import feature.tree.data.model.TreeListDTO
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface TreeApi {
    @POST("api/v1/geneology/tree")
    suspend fun createTree(@Body body: TreeRequestDTO): ApiResult<TreeDTO>

    @GET("api/v1/geneology/tree/graph")
    suspend fun getTreeGraph(
        @Query("tree_id") treeId: String,
        @Query("target_person_id") targetPersonId: String,
        @Query("depth") depth: Int,
        @Query("preview") preview: Boolean,
    ): ApiResult<TreeGraphDTO>

    @GET("api/v1/geneology/tree/list")
    suspend fun getTreeList(): ApiResult<TreeListDTO>

    @DELETE("api/v1/geneology/tree/{treeId}")
    suspend fun deleteTree(@Path("treeId") treeId: String): ApiResult<Unit>

    @PATCH("api/v1/geneology/tree/{treeId}")
    suspend fun updateTree(
        @Path("treeId") treeId: String,
        @Body body: TreeRequestDTO,
    ): ApiResult<TreeDTO>

    @GET("api/v1/geneology/tree/{treeId}/export/gedcom")
    suspend fun exportGedcom(@Path("treeId") treeId: String): ApiResult<Unit>

    @POST("api/v1/geneology/tree/{treeId}/import/gedcom")
    suspend fun importGedcom(@Path("treeId") treeId: String): ApiResult<Unit>
}