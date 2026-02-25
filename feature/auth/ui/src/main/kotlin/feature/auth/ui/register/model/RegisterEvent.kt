package feature.auth.ui.register.model

sealed interface RegisterEvent {
    data object OnSendCredentials: RegisterEvent
}