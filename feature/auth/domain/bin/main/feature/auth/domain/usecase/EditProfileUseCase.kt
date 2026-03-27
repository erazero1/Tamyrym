package feature.auth.domain.usecase

import core.domain.result.ApiResult
import feature.auth.domain.AuthRepository
import feature.auth.domain.model.User
import feature.auth.domain.model.UserUpdate

class EditProfileUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(userUpdate: UserUpdate): ApiResult<User> {
        return repository.editProfile(userUpdate)
    }
}