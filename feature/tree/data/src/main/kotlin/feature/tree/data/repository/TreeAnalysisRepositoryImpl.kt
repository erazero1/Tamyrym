package feature.tree.data.repository

import core.domain.result.ApiResult
import core.domain.result.map
import feature.tree.data.model.ai.toDomain
import feature.tree.data.remote.TreeAiApi
import feature.tree.domain.model.ai.TreeAnalysisResult
import feature.tree.domain.repository.TreeAnalysisRepository

class TreeAnalysisRepositoryImpl(
    private val api: TreeAiApi,
) : TreeAnalysisRepository {
    override suspend fun analyzeTree(treeId: String): ApiResult<TreeAnalysisResult> {
        return api.analyzeTree(treeId).map { it.toDomain() }
    }
}