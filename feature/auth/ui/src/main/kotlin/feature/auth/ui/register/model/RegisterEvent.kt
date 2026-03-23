package feature.auth.ui.register.model

import core.domain.model.Gender


internal sealed interface RegisterEvent {
    data class OnFirstNameChanged(val value: String) : RegisterEvent
    data class OnLastNameChanged(val value: String) : RegisterEvent
    data class OnGenderChanged(val value: Gender) : RegisterEvent
    data class OnBirthYearSelected(val value: Int) : RegisterEvent
    data class OnEmailChanged(val value: String) : RegisterEvent
    data class OnPasswordChanged(val value: String) : RegisterEvent
    data object OnPasswordVisibilityToggle : RegisterEvent
    data object OnContinueClick : RegisterEvent
    data object OnBackClick : RegisterEvent
    data object OnCompleteRegistrationClick : RegisterEvent
}