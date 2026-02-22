package feature.auth.ui.login.model

internal sealed interface LoginEvent {
    data class OnSendCredentials(val email: String, val password: String): LoginEvent
}