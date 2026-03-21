package core.data.network.api

import core.data.common.model.LoginRequestDTO
import core.data.common.model.LogoutRequestDTO
import core.data.common.model.LogoutResponseDTO
import core.data.common.model.RefreshAccessTokenRequestDTO
import core.data.common.model.RegisterRequestDTO
import core.data.common.model.RegisterResponseDTO
import core.data.common.model.Token
import core.data.common.model.UserUpdateRequestDTO
import core.data.common.model.UserWrapper
import core.domain.result.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body body: LoginRequestDTO,
    ): ApiResult<Token>

    @POST("api/v1/auth/logout")
    suspend fun logout(
        @Body body: LogoutRequestDTO,
    ): ApiResult<LogoutResponseDTO>

    @GET("api/v1/auth/me")
    suspend fun getProfile(): ApiResult<UserWrapper>

    @POST("api/v1/auth/refresh")
    suspend fun refreshAccessToken(
        @Body body: RefreshAccessTokenRequestDTO,
    ): ApiResult<Token>

    @PATCH("api/v1/auth/me")
    suspend fun editProfile(
        @Body body: UserUpdateRequestDTO,
    ): ApiResult<UserWrapper>

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body body: RegisterRequestDTO,
    ): ApiResult<RegisterResponseDTO>
}