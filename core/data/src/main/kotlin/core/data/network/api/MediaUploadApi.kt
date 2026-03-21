package core.data.network.api

import core.domain.result.ApiResult
import okhttp3.RequestBody
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Url

interface MediaUploadApi {
    @PUT
    suspend fun uploadMediaPut(
        @Url url: String,
        @Header("Content-Type") contentType: String,
        @Body media: RequestBody,
    ): ApiResult<Unit>

    @POST
    suspend fun uploadMediaPost(
        @Url url: String,
        @Header("Content-Type") contentType: String,
        @Body media: RequestBody,
    ): ApiResult<Unit>
}