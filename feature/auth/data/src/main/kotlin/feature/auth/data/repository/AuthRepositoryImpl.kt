package feature.auth.data.repository

import core.data.common.model.GoogleOAuthRequestDTO
import core.data.common.model.LoginRequestDTO
import core.data.common.model.LogoutRequestDTO
import core.data.common.model.RegisterRequestDTO
import core.data.common.model.UserUpdateRequestDTO
import core.data.local.TokenManager
import core.data.network.api.AuthApi
import core.domain.result.ApiError
import core.domain.result.ApiException
import core.domain.result.ApiResult
import core.domain.result.ApiSuccess
import core.domain.result.map
import feature.auth.data.model.toDomain
import feature.auth.domain.AuthRepository
import feature.auth.domain.model.User
import feature.auth.domain.model.UserRegistrationInfo
import feature.auth.domain.model.UserUpdate

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val tokenManager: TokenManager,
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String,
    ): ApiResult<Unit> {
        val result = authApi.login(
            body = LoginRequestDTO(
                email = email,
                password = password,
            )
        )

        return when (result) {
            is ApiSuccess -> {
                tokenManager.saveToken(result.data)
                ApiSuccess(Unit)
            }

            is ApiError -> result
            is ApiException -> result
        }
    }

    override suspend fun googleOAuth(idToken: String): ApiResult<Unit> {
        val result = authApi.googleOAuth(
            body = GoogleOAuthRequestDTO(idToken = idToken),
        )

        return when (result) {
            is ApiSuccess -> {
                tokenManager.saveToken(result.data.tokens)
                ApiSuccess(Unit)
            }

            is ApiError -> result
            is ApiException -> result
        }
    }

    override suspend fun isLoggedIn(): Boolean {
        return tokenManager.isLoggedIn()
    }

    override suspend fun logout(): ApiResult<Unit> {
        val token = tokenManager.getToken()
        return when (val result = authApi.logout(
            body = LogoutRequestDTO(refreshToken = token?.refreshToken ?: "")
        )) {
            is ApiError -> result
            is ApiException -> result
            is ApiSuccess<*> -> {
                tokenManager.deleteToken()
                ApiSuccess(Unit)
            }
        }
    }

    override suspend fun register(userInfo: UserRegistrationInfo): ApiResult<Unit> {
        val result = authApi.register(
            body = RegisterRequestDTO(
                birthYear = userInfo.birthYear,
                email = userInfo.email,
                firstName = userInfo.firstName,
                gender = userInfo.gender,
                lastName = userInfo.lastName,
                password = userInfo.password,
            )
        )

        return when (result) {
            is ApiSuccess -> {
                tokenManager.saveToken(token = result.data.token)
                ApiSuccess(Unit)
            }

            is ApiError -> result
            is ApiException -> result
        }
    }

    override suspend fun getProfile(): ApiResult<User> {
        return authApi.getProfile().map { it.userDTO.toDomain() }
    }

    override suspend fun editProfile(userUpdate: UserUpdate): ApiResult<User> {
        return authApi.editProfile(
            body = UserUpdateRequestDTO(
                birthYear = userUpdate.birthYear,
                firstName = userUpdate.firstName,
                gender = userUpdate.gender.value,
                lastName = userUpdate.lastName,
            )
        ).map { it.userDTO.toDomain() }
    }
}
