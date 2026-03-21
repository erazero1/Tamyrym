package core.data.remote

import com.google.gson.GsonBuilder
import core.data.BuildConfig
import core.data.common.result.NetworkResultCallAdapterFactory
import core.data.local.TokenManager
import core.data.network.TokenAuthenticator
import core.data.network.api.AuthApi
import core.data.network.api.MediaApi
import core.data.network.api.MediaUploadApi
import core.data.network.interceptor.AuthInterceptor
import core.data.network.repository.MediaRepositoryImpl
import core.data.network.repository.MediaUploadRepositoryImpl
import core.domain.repository.MediaRepository
import core.domain.repository.MediaUploadRepository
import core.domain.usecase.ConfirmMediaUploadUseCase
import core.domain.usecase.GetMediaUploadUrlUseCase
import core.domain.usecase.GetPersonMediaUseCase
import core.domain.usecase.GetTreeMediaUseCase
import core.domain.usecase.UploadMediaUseCase
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

    single<MediaApi> {
        get<Retrofit>().create(MediaApi::class.java)
    }


    // Repositories
    factory<MediaRepository> {
        MediaRepositoryImpl(api = get<MediaApi>())
    }

    factory<MediaUploadRepository> {
        MediaUploadRepositoryImpl(api = get<MediaUploadApi>())
    }

    // Use cases
    factory<ConfirmMediaUploadUseCase> {
        ConfirmMediaUploadUseCase(repository = get<MediaRepository>())
    }
    factory<GetMediaUploadUrlUseCase> {
        GetMediaUploadUrlUseCase(repository = get<MediaRepository>())
    }
    factory<GetPersonMediaUseCase> {
        GetPersonMediaUseCase(repository = get<MediaRepository>())
    }
    factory<GetPersonMediaUseCase> {
        GetPersonMediaUseCase(repository = get<MediaRepository>())
    }
    factory<GetTreeMediaUseCase> {
        GetTreeMediaUseCase(repository = get<MediaRepository>())
    }
    factory<UploadMediaUseCase> {
        UploadMediaUseCase(repository = get<MediaUploadRepository>())
    }
}
