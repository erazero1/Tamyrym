package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.model.Tree
import feature.tree.domain.model.TreeRequest
import feature.tree.domain.repository.TreeRepository

class UpdateTreeUseCase(
    private val repository: TreeRepository,
) {
    suspend operator fun invoke(treeId: String, treeRequest: TreeRequest): ApiResult<Tree> {
        return repository.updateTree(
            treeId = treeId,
            body = treeRequest,
        )
    }
}