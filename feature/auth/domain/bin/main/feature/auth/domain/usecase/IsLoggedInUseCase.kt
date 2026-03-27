package feature.auth.domain.usecase

import feature.auth.domain.AuthRepository

class IsLoggedInUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(): Boolean {
        return repository.isLoggedIn()
    }
}