package core.data.network.interceptor

import core.data.local.TokenManager
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response

internal class AuthInterceptor(private val tokenManager: TokenManager): Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking {
            tokenManager.tokenFlow.first()
        }

        val request = chain.request().newBuilder().apply {
            token?.let {
                addHeader("Authorization", "Bearer ${it.accessToken}")
            }
        }.build()
        return chain.proceed(request)
    }
}