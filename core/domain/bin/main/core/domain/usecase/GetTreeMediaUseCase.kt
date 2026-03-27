package core.domain.usecase

import core.domain.repository.MediaRepository
import core.domain.result.ApiResult

class GetTreeMediaUseCase(private val repository: MediaRepository) {
    suspend operator fun invoke(treeId: String): ApiResult<Unit> {
        return repository.getTreeMedia(treeId = treeId)
    }
}