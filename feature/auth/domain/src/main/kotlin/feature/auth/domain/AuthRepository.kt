package feature.auth.domain

import core.domain.result.ApiResult
import feature.auth.domain.model.User
import feature.auth.domain.model.UserRegistrationInfo
import feature.auth.domain.model.UserUpdate

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
    ): ApiResult<Unit>

    suspend fun isLoggedIn(): Boolean
    suspend fun logout(): ApiResult<Unit>
    suspend fun register(userInfo: UserRegistrationInfo): ApiResult<Unit>
    suspend fun getProfile(): ApiResult<User>
    suspend fun editProfile(userUpdate: UserUpdate): ApiResult<User>
}