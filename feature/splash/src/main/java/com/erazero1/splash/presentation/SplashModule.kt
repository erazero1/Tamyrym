package com.erazero1.splash.presentation

import feature.auth.domain.usecase.IsLoggedInUseCase
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val splashModule = module {
    viewModel<SplashViewModel> {
        SplashViewModel(isLoggedInUseCase = get<IsLoggedInUseCase>())
    }
}