package core.data.common.result

import core.data.common.extensions.getErrorBody
import core.domain.result.ApiError
import core.domain.result.ApiException
import core.domain.result.ApiResult
import core.domain.result.ApiSuccess
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Request
import okio.Timeout
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import retrofit2.awaitResponse

class NetworkResultCall<T : Any>(
    private val proxy: Call<T>,
    private val coroutineScope: CoroutineScope
) : Call<ApiResult<T>> {
    override fun enqueue(callback: Callback<ApiResult<T>>) {
        coroutineScope.launch {
            try {
                val response = proxy.awaitResponse()
                val apiResponse = handleApi { response }
                callback.onResponse(this@NetworkResultCall, Response.success(apiResponse))
            } catch (e: Exception) {
                callback.onResponse(
                    this@NetworkResultCall,
                    Response.success(ApiException(e))
                )
            }
        }
    }

    override fun execute(): Response<ApiResult<T>> =
        runBlocking(coroutineScope.coroutineContext) {
            val response = proxy.execute()
            val apiResponse = handleApi { response }
            Response.success(apiResponse)
        }

    override fun clone(): Call<ApiResult<T>> = NetworkResultCall(proxy.clone(), coroutineScope)
    override fun request(): Request = proxy.request()
    override fun isExecuted(): Boolean = proxy.isExecuted
    override fun timeout(): Timeout = proxy.timeout()
    override fun isCanceled(): Boolean = proxy.isCanceled
    override fun cancel() = proxy.cancel()

    private suspend fun handleApi(
        execute: suspend () -> Response<T>
    ): ApiResult<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                ApiSuccess(body)
            } else {
                val errorBody = HttpException(response).getErrorBody()
                ApiError(
                    code = response.code(),
                    details = errorBody?.details,
                    error = errorBody?.error
                )
            }
        } catch (e: HttpException) {
            ApiError(
                code = e.code(),
                details = e.message(),
                error = e.getErrorBody()?.error
            )
        } catch (e: Throwable) {
            ApiException(e)
        }
    }
}
