package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.TreeGraph
import feature.tree.domain.repository.TreeRepository

class GetTreeGraphUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(
        treeId: String,
        targetPersonId: String,
        depth: Int,
        preview: Boolean,
    ): ApiResult<TreeGraph> {
        return repository.getTreeGraph(
            treeId = treeId,
            targetPersonId = targetPersonId,
            depth = depth,
            preview = preview,
        )
    }
}