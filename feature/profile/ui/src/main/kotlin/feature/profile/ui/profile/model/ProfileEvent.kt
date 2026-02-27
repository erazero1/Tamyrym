package feature.profile.ui.profile.model

sealed interface ProfileEvent {
    data object LoadProfile: ProfileEvent
    data object OnLogoutClick : ProfileEvent
}
