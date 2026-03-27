package feature.auth.domain.usecase

import core.domain.result.ApiResult
import feature.auth.domain.AuthRepository

class LogoutUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): ApiResult<Unit> {
        return repository.logout()
    }
}