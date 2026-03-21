package core.data.network

import android.util.Log
import core.data.common.model.RefreshAccessTokenRequestDTO
import core.data.common.model.Token
import core.data.local.TokenManager
import core.data.network.api.AuthApi
import core.domain.result.onError
import core.domain.result.onSuccess
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

internal class TokenAuthenticator(
    private val tokenManager: TokenManager,
    private val authApi: AuthApi,
) : Authenticator {
    companion object {
        private const val LOG_TAG = "TokenAuthenticator"
    }

    override fun authenticate(route: Route?, response: Response): Request? {
        val token = runBlocking { tokenManager.tokenFlow.first() }
        val refreshToken = token?.refreshToken ?: return null

        return synchronized(this) {
            val newToken = runBlocking { tokenManager.tokenFlow.first() }

            val tokenToUse = if (newToken?.accessToken != token.accessToken) {
                newToken
            } else {
                runBlocking {
                    var resultToken: Token? = null
                    authApi.refreshAccessToken(RefreshAccessTokenRequestDTO(refreshToken))
                        .onSuccess {
                            tokenManager.saveToken(it)
                            resultToken = it
                        }
                        .onError { code, message, _ ->
                            Log.e(LOG_TAG, "Refresh failed: $code $message")
                            tokenManager.deleteToken()
                        }
                    resultToken
                }
            }

            tokenToUse?.let {
                response.request.newBuilder()
                    .header("Authorization", "Bearer ${it.accessToken}")
                    .build()
            }
        }
    }
}