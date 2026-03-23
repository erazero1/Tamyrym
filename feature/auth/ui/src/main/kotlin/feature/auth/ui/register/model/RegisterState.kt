package feature.auth.ui.register.model

import core.domain.model.Gender


internal sealed class RegisterState {
    data object Loading : RegisterState()
    data object Success : RegisterState()
    data class Error(val message: String? = null) : RegisterState()
    data class Content(
        val currentStep: Int = 1,
        val firstName: String = "",
        val lastName: String = "",
        val gender: Gender = Gender.MALE,
        val birthYear: Int? = null,
        val email: String = "",
        val password: String = "",
        val isPasswordVisible: Boolean = false,
        val emailError: Boolean = false,
        val passwordError: Boolean = false,
    ) : RegisterState()
}
