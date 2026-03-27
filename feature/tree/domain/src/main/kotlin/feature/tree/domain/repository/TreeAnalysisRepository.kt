package feature.tree.domain.repository

import core.domain.result.ApiResult
import feature.tree.domain.model.ai.TreeAnalysisResult

interface TreeAnalysisRepository {
    suspend fun analyzeTree(treeId: String): ApiResult<TreeAnalysisResult>
}