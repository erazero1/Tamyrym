package feature.auth.domain.usecase

import feature.auth.domain.AuthRepository

class GoogleOAuthUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(idToken: String) = repository.googleOAuth(idToken)
}