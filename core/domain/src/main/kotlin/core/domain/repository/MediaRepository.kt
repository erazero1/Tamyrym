package core.domain.repository

import core.domain.model.GetUploadUrlRequest
import core.domain.model.UploadUrlResponse
import core.domain.result.ApiResult

interface MediaRepository {
    suspend fun confirmUpload(objectKey: String): ApiResult<Unit>

    suspend fun getMediaUploadUrl(getUploadUrlRequest: GetUploadUrlRequest): ApiResult<UploadUrlResponse>

    suspend fun getPersonMedia(personId: String): ApiResult<Unit>

    suspend fun getTreeMedia(treeId: String): ApiResult<Unit>

    suspend fun getPersonMediaUploadUrl(mediaId: String): ApiResult<UploadUrlResponse>
}