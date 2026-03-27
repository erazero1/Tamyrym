package feature.auth.domain.usecase

import core.domain.result.ApiResult
import feature.auth.domain.AuthRepository
import feature.auth.domain.model.UserRegistrationInfo

class RegisterUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(userInfo: UserRegistrationInfo): ApiResult<Unit> {
        return repository.register(userInfo = userInfo)
    }
}