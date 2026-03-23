package feature.auth.ui.auth_options.model

sealed interface AuthOptionsState {
    data object Idle : AuthOptionsState
    data object Loading : AuthOptionsState
    data class Error(val message: String? = null) : AuthOptionsState
}
