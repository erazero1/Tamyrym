package core.data.network.api

import core.data.common.model.ConfirmUploadRequestDTO
import core.data.common.model.GetUploadUrlRequestDTO
import core.data.common.model.UploadUrlResponseDTO
import core.domain.result.ApiResult
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MediaApi {
    @POST("/api/v1/media/avatar/confirm")
    suspend fun confirmUpload(
        @Body body: ConfirmUploadRequestDTO,
    ): ApiResult<Unit>

    @POST("/api/v1/media/avatar/upload")
    suspend fun getMediaUploadUrl(
        @Body body: GetUploadUrlRequestDTO,
    ): ApiResult<UploadUrlResponseDTO>

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
    ): ApiResult<UploadUrlResponseDTO>
}