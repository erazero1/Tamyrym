package feature.auth.ui.auth_options.model

sealed interface AuthOptionsEvent {
    data object OnLoginClick : AuthOptionsEvent
    data object OnRegisterClick : AuthOptionsEvent
    data object OnGoogleSignInClick : AuthOptionsEvent
    data class OnGoogleSignInResult(val idToken: String) : AuthOptionsEvent
}
