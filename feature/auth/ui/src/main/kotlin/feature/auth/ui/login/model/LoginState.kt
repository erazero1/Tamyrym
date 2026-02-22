package feature.auth.ui.login.model

internal sealed class LoginState {
    data object Initial: LoginState()
    data class Error(val message: String?): LoginState()
    data object Loading: LoginState()
    data object Success: LoginState()
}