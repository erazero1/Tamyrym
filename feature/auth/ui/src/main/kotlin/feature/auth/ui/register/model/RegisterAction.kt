package feature.auth.ui.register.model

internal sealed interface RegisterAction {
    data object RegistrationSuccess : RegisterAction
    data class ShowError(val message: String? = null) : RegisterAction
    data object NotAllFieldsFilled : RegisterAction
}