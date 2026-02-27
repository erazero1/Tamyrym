package feature.profile.ui.profile.model

sealed interface ProfileAction {
    data object OnLogoutClick : ProfileAction
    data class ShowToast(val message: String? = null): ProfileAction
}
