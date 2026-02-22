package feature.auth.ui.login.model

internal sealed interface LoginAction {
    data object SuccessfulLogin: LoginAction
    data class ShowToast(val message: String? = null): LoginAction
}