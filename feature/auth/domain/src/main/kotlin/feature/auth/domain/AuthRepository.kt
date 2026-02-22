package feature.auth.domain

import core.domain.result.ApiResult
import feature.auth.domain.model.UserRegistrationInfo

interface AuthRepository {
    suspend fun login(
        email: String,
        password: String,
    ): ApiResult<Unit>

    suspend fun logout(): ApiResult<Unit>
    suspend fun register(userInfo: UserRegistrationInfo): ApiResult<Unit>
}