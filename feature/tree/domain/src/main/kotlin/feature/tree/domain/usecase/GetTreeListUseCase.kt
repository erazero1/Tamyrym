package feature.tree.domain.usecase

import core.domain.model.Tree
import core.domain.result.ApiResult
import feature.tree.domain.repository.TreeRepository

class GetTreeListUseCase(private val repository: TreeRepository) {
    suspend operator fun invoke(): ApiResult<List<Tree>> {
        return repository.getTreeList()
    }
}