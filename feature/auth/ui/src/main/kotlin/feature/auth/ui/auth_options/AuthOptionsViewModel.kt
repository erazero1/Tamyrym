package feature.auth.ui.auth_options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onException
import core.domain.result.onSuccess
import feature.auth.domain.usecase.GoogleOAuthUseCase
import feature.auth.ui.auth_options.model.AuthOptionsAction
import feature.auth.ui.auth_options.model.AuthOptionsEvent
import feature.auth.ui.auth_options.model.AuthOptionsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AuthOptionsViewModel(
    private val googleOAuthUseCase: GoogleOAuthUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<AuthOptionsState>(AuthOptionsState.Idle)
    val state = _state.asStateFlow()

    private val _action = Channel<AuthOptionsAction>(Channel.BUFFERED)
    val action: Flow<AuthOptionsAction> = _action.receiveAsFlow()

    internal fun onEvent(event: AuthOptionsEvent) {
        viewModelScope.launch {
            when (event) {
                AuthOptionsEvent.OnGoogleSignInClick -> _action.send(AuthOptionsAction.LaunchGoogleSignIn)
                AuthOptionsEvent.OnLoginClick -> _action.send(AuthOptionsAction.NavigateToLogin)
                AuthOptionsEvent.OnRegisterClick -> _action.send(AuthOptionsAction.NavigateToRegister)
                is AuthOptionsEvent.OnGoogleSignInResult -> onGoogleSignInResult(event.idToken)
            }
        }
    }

    private fun onGoogleSignInResult(idToken: String) {
        viewModelScope.launch {
            googleOAuthUseCase(idToken = idToken).onSuccess {
                _action.send(AuthOptionsAction.OnGoogleSignInSuccess)
            }.onError { _, message, _ ->
                _state.update { AuthOptionsState.Error(message) }
            }.onException { _ ->
                _state.update {
                    AuthOptionsState.Error()
                }
            }
        }
    }
}
