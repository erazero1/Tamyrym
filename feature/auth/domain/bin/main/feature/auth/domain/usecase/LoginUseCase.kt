package feature.auth.domain.usecase

import core.domain.result.ApiResult
import feature.auth.domain.AuthRepository

class LoginUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(
        email: String,
        password: String,
    ): ApiResult<Unit> {
        return repository.login(email = email, password = password)
    }
}