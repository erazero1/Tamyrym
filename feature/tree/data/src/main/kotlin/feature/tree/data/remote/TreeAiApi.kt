package feature.tree.data.remote

import core.domain.result.ApiResult
import feature.tree.data.model.ai.TreeAnalysisResultDTO
import retrofit2.http.POST
import retrofit2.http.Path

interface TreeAiApi {
    @POST("api/v1/ai/tree/{tree_id}/analyze")
    suspend fun analyzeTree(@Path("tree_id") treeId: String): ApiResult<TreeAnalysisResultDTO>
}