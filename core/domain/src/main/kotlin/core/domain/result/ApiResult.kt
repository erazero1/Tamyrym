package core.domain.result

sealed interface ApiResult<out T>

class ApiSuccess<T>(val data: T) : ApiResult<T>
suspend inline fun <T : Any> ApiResult<T>.onSuccess(
    noinline executable: suspend (T) -> Unit
): ApiResult<T> = apply {
    if (this is ApiSuccess) {
        executable(data)
    }
}

class ApiError(val code: Int, val details: String?, val error: String?) : ApiResult<Nothing>
suspend inline fun <T : Any> ApiResult<T>.onError(
    noinline executable: suspend (code: Int, message: String?, serverCode: String?) -> Unit
): ApiResult<T> = apply {
    if (this is ApiError) {
        executable(code, details, error)
    }
}

class ApiException(val e: Throwable) : ApiResult<Nothing>
suspend inline fun <T : Any> ApiResult<T>.onException(
    noinline executable: suspend (e: Throwable) -> Unit
): ApiResult<T> = apply {
    if (this is ApiException) {
        executable(e)
    }
}

inline fun <T, R> ApiResult<T>.map(onSuccess: (T) -> R): ApiResult<R> = when (this) {
    is ApiError -> this
    is ApiException -> this
    is ApiSuccess -> ApiSuccess(onSuccess(this.data))
}

