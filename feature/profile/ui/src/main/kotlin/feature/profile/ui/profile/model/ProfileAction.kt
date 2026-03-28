package feature.profile.ui.profile.model

sealed interface ProfileAction {
    data object OnLogoutClick : ProfileAction
    data class ShowToast(val message: String? = null): ProfileAction
    data class ProfileUpdated(val message: String) : ProfileAction
}
