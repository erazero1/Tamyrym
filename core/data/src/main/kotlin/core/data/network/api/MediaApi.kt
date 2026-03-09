package core.data.network.api

import core.data.common.model.ConfirmAvatarUploadRequest
import core.data.common.model.GetAvatarUploadUrlRequest
import core.data.common.model.UploadUrlResponse
import core.domain.result.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MediaApi {
    @POST("/api/v1/media/avatar/confirm")
    suspend fun confirmAvatarUpload(
        @Body body: ConfirmAvatarUploadRequest,
    ): ApiResult<Unit>

    @POST("/api/v1/media/avatar/upload")
    suspend fun getAvatarUploadUrl(
        @Body body: GetAvatarUploadUrlRequest,
    ): ApiResult<UploadUrlResponse>

    // TODO
    @GET("/api/v1/media/person/{personId}")
    suspend fun getPersonMedia(
        @Path("personId") personId: String,
    ): ApiResult<Unit>

    // TODO
    @GET("/api/v1/media/tree/{treeId}")
    suspend fun getTreeMedia(
        @Path("treeId") treeId: String,
    ): ApiResult<Unit>

    @GET("/api/v1/media/{mediaId}")
    suspend fun getPersonMediaUploadUrl(
        @Path("mediaId") mediaId: String,
    ): ApiResult<UploadUrlResponse>
}