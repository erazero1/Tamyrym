package feature.tree.domain.usecase

import core.domain.model.Tree
import core.domain.result.ApiResult
import feature.tree.domain.model.TreeRequest
import feature.tree.domain.repository.TreeRepository

class CreateNewTreeUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(treeRequest: TreeRequest): ApiResult<Tree> {
        return repository.createNewTree(body = treeRequest)
    }
}