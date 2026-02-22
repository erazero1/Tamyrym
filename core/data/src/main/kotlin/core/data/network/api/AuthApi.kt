package core.data.network.api

import core.data.common.model.LoginRequest
import core.data.common.model.ProfileEditRequest
import core.data.common.model.RefreshAccessTokenRequest
import core.data.common.model.RegisterRequest
import core.data.common.model.RegisterResponse
import core.data.common.model.Token
import core.data.common.model.UserWrapper
import core.domain.result.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthApi {
    @POST("/api/v1/auth/login")
    suspend fun login(
        @Body body: LoginRequest,
    ): ApiResult<Token>

    @POST("api/v1/auth/logout")
    suspend fun logout(): ApiResult<Unit>

    @GET("api/v1/auth/me")
    suspend fun getProfile(): ApiResult<UserWrapper>

    @POST("api/v1/auth/refresh")
    suspend fun refreshAccessToken(
        @Body body: RefreshAccessTokenRequest,
    ): ApiResult<Token>

    @PATCH("api/v1/auth/me")
    suspend fun editProfile(
        @Body body: ProfileEditRequest,
    ): ApiResult<UserWrapper>

    @POST("api/v1/auth/register")
    suspend fun register(
        @Body body: RegisterRequest,
    ): ApiResult<RegisterResponse>
}