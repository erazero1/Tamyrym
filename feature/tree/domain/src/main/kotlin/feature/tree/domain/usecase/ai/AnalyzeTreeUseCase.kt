package feature.tree.domain.usecase.ai

import core.domain.result.ApiResult
import feature.tree.domain.model.ai.TreeAnalysisResult
import feature.tree.domain.repository.TreeAnalysisRepository

class AnalyzeTreeUseCase(
    private val repository: TreeAnalysisRepository,
) {
    suspend operator fun invoke(treeId: String): ApiResult<TreeAnalysisResult> {
        return repository.analyzeTree(treeId)
    }
}