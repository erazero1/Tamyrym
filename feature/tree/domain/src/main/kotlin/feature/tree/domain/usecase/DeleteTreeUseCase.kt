package feature.tree.domain.usecase

import core.domain.result.ApiResult
import feature.tree.domain.repository.TreeRepository

class DeleteTreeUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(treeId: String): ApiResult<Unit> {
        return repository.deleteTree(treeId = treeId)
    }
}