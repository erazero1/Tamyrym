package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.TreeGraph
import feature.tree.domain.repository.TreeRepository

class GetOptimizedTreeGraphUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(
        treeId: String,
        targetPersonId: String,
        depth: Int,
    ): ApiResult<TreeGraph> {
        return repository.getOptimizedTreeGraph(
            treeId = treeId,
            targetPersonId = targetPersonId,
            depth = depth
        )
    }
}