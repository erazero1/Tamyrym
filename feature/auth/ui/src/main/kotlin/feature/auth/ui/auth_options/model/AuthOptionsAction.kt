package feature.auth.ui.auth_options.model

sealed interface AuthOptionsAction {
    data object NavigateToLogin : AuthOptionsAction
    data object NavigateToRegister : AuthOptionsAction
    data object HandleGoogleSignIn : AuthOptionsAction
}
