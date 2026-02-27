package feature.profile.di

import feature.auth.domain.usecase.GetProfileUseCase
import feature.auth.domain.usecase.LogoutUseCase
import feature.profile.ui.profile.ProfileViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val profileModule = module {
    viewModel<ProfileViewModel> {
        ProfileViewModel(
            getProfileUseCase = get<GetProfileUseCase>(),
            onLogoutUseCase = get<LogoutUseCase>(),
        )
    }
}