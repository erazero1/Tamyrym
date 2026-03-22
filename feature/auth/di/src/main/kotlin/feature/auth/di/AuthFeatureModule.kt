package feature.auth.di

import core.data.local.TokenManager
import core.data.network.api.AuthApi
import feature.auth.data.repository.AuthRepositoryImpl
import feature.auth.domain.AuthRepository
import feature.auth.domain.usecase.EditProfileUseCase
import feature.auth.domain.usecase.GetProfileUseCase
import feature.auth.domain.usecase.IsLoggedInUseCase
import feature.auth.domain.usecase.LoginUseCase
import feature.auth.domain.usecase.LogoutUseCase
import feature.auth.domain.usecase.RegisterUseCase
import feature.auth.ui.auth_options.AuthOptionsViewModel
import feature.auth.ui.login.LoginViewModel
import feature.auth.ui.register.RegisterViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val authFeatureModule = module {

    single<AuthRepository> {
        AuthRepositoryImpl(
            authApi = get<AuthApi>(),
            tokenManager = get<TokenManager>()
        )
    }

    factory<LoginUseCase> {
        LoginUseCase(get<AuthRepository>())
    }

    factory<LogoutUseCase> {
        LogoutUseCase(get<AuthRepository>())
    }

    factory<RegisterUseCase> {
        RegisterUseCase(get<AuthRepository>())
    }

    factory<IsLoggedInUseCase> {
        IsLoggedInUseCase(get<AuthRepository>())
    }

    factory<GetProfileUseCase> {
        GetProfileUseCase(get<AuthRepository>())
    }

    factory<EditProfileUseCase> {
        EditProfileUseCase(get<AuthRepository>())
    }

    viewModel<LoginViewModel> {
        LoginViewModel(
            loginUseCase = get<LoginUseCase>(),
        )
    }

    viewModel<RegisterViewModel> {
        RegisterViewModel(
            registerUseCase = get<RegisterUseCase>(),
        )
    }

    viewModel<AuthOptionsViewModel> {
        AuthOptionsViewModel()
    }
}