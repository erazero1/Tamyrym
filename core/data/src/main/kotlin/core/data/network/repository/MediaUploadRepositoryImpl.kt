package core.data.network.repository

import core.data.network.api.MediaUploadApi
import core.domain.model.ContentType
import core.domain.model.UploadUrlResponse
import core.domain.repository.MediaUploadRepository
import core.domain.result.ApiException
import core.domain.result.ApiResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class MediaUploadRepositoryImpl(private val api: MediaUploadApi) : MediaUploadRepository {
    override suspend fun uploadMedia(
        uploadDetails: UploadUrlResponse,
        mediaFile: File,
        contentType: ContentType,
    ): ApiResult<Unit> = withContext(Dispatchers.IO) {
        try {
            val mediaType = contentType.type.toMediaTypeOrNull()
            val requestBody = mediaFile.asRequestBody(mediaType)
            when (uploadDetails.method.uppercase()) {
                "PUT" -> api.uploadMediaPut(
                    url = uploadDetails.url,
                    contentType = contentType.type,
                    media = requestBody
                )

                "POST" -> api.uploadMediaPost(
                    url = uploadDetails.url,
                    contentType = contentType.type,
                    media = requestBody,
                )

                else -> error("The upload method for media is not PUT or POST")
            }
        } catch (e: Exception) {
            ApiException(e = e)
        }
    }
}