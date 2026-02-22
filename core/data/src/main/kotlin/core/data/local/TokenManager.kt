package core.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import core.data.common.Constants
import core.data.common.model.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenManager(
    private val context: Context,
) {
    companion object {
        private val ACCESS_TOKEN = stringPreferencesKey(Constants.ACCESS_TOKEN_KEY)
        private val REFRESH_TOKEN = stringPreferencesKey(Constants.REFRESH_TOKEN_KEY)
    }

    private val Context.dataStore by preferencesDataStore(name = Constants.AUTH_DATA_STORE_PREFS)

    val tokenFlow: Flow<Token?> = context.dataStore.data.map { preferences ->
        val accessToken = preferences[ACCESS_TOKEN]
        val refreshToken = preferences[REFRESH_TOKEN]
        if (accessToken != null && refreshToken != null) Token(
            accessToken = accessToken,
            refreshToken = refreshToken
        ) else null
    }

    suspend fun saveToken(token: Token) {
        context.dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token.accessToken
            preferences[REFRESH_TOKEN] = token.refreshToken
        }
    }

    suspend fun deleteToken() {
        context.dataStore.edit { preferences ->
            preferences.clear()
        }
    }
}