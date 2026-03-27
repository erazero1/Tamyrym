package feature.auth.domain.usecase

import core.domain.result.ApiResult
import feature.auth.domain.AuthRepository
import feature.auth.domain.model.User

class GetProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): ApiResult<User> {
        return repository.getProfile()
    }
}