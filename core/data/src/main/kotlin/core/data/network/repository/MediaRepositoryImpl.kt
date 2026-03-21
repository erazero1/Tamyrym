package core.data.network.repository

import core.data.common.model.ConfirmUploadRequestDTO
import core.data.common.model.GetUploadUrlRequestDTO
import core.data.common.model.toDomain
import core.data.network.api.MediaApi
import core.domain.model.GetUploadUrlRequest
import core.domain.model.UploadUrlResponse
import core.domain.repository.MediaRepository
import core.domain.result.ApiResult
import core.domain.result.map

class MediaRepositoryImpl(private val api: MediaApi) : MediaRepository {
    override suspend fun confirmUpload(objectKey: String): ApiResult<Unit> {
        return api.confirmUpload(
            body = ConfirmUploadRequestDTO(
                objectKey = objectKey,
            )
        )
    }

    override suspend fun getMediaUploadUrl(getUploadUrlRequest: GetUploadUrlRequest): ApiResult<UploadUrlResponse> {
        return api.getMediaUploadUrl(
            body = GetUploadUrlRequestDTO(
                contentType = getUploadUrlRequest.contentType.type,
                filename = getUploadUrlRequest.filename,
            )
        ).map { it.toDomain() }
    }

    override suspend fun getPersonMedia(personId: String): ApiResult<Unit> {
        return api.getPersonMedia(personId = personId)
    }

    override suspend fun getTreeMedia(treeId: String): ApiResult<Unit> {
        return api.getTreeMedia(treeId)
    }

    override suspend fun getPersonMediaUploadUrl(mediaId: String): ApiResult<UploadUrlResponse> {
        return api.getPersonMediaUploadUrl(mediaId).map { it.toDomain() }
    }

}