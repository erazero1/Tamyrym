package feature.auth.ui.register.model

sealed class RegisterState {
    data object Initial : RegisterState()
    data object Loading : RegisterState()
    data object Success : RegisterState()
    data class Error(val message: String? = null) : RegisterState()
}