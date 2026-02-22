package core.data.remote

import com.google.gson.GsonBuilder
import core.data.BuildConfig
import core.data.common.result.NetworkResultCallAdapterFactory
import core.data.local.TokenManager
import core.data.network.TokenAuthenticator
import core.data.network.api.AuthApi
import core.data.network.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val remoteDataModule = module {
    single {
        GsonBuilder().create()
    }

    single {
        AuthInterceptor(get<TokenManager>())
    }

    // Retrofit instance without authenticator
    single(named("AuthRetrofit")) {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                    .build()
            )
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        val authApi = get<Retrofit>(named("AuthRetrofit")).create(AuthApi::class.java)
        TokenAuthenticator(get<TokenManager>(), authApi)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .authenticator(get<TokenAuthenticator>())
            .addInterceptor(get<AuthInterceptor>()).build()
    }

    single {
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(NetworkResultCallAdapterFactory.create())
            .build()
    }

    single<AuthApi> {
        get<Retrofit>().create(AuthApi::class.java)
    }
}
