package feature.auth.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onException
import core.domain.result.onSuccess
import feature.auth.domain.usecase.LoginUseCase
import feature.auth.ui.login.model.LoginAction
import feature.auth.ui.login.model.LoginEvent
import feature.auth.ui.login.model.LoginState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<LoginState> = MutableStateFlow(LoginState.Initial)
    internal val state: StateFlow<LoginState> = _state.asStateFlow()

    private val _action: Channel<LoginAction> = Channel(capacity = Channel.BUFFERED)
    internal val action: Flow<LoginAction> = _action.receiveAsFlow()

    internal fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.OnSendCredentials -> onSendCredentials(event.email, event.password)
        }
    }

    private fun onSendCredentials(email: String, password: String) {
        viewModelScope.launch {
            _state.value = LoginState.Loading
            loginUseCase(email = email, password = password)
                .onSuccess {
                    _state.value = LoginState.Success
                    _action.send(LoginAction.SuccessfulLogin)
                    debug("Successful login.")
                }
                .onError { _, message, _ ->
                    _state.value = LoginState.Error(message)
                    _action.send(LoginAction.ShowToast(message = message))
                    debug("Login Error: $message")
                }
                .onException { e ->
                    _state.value = LoginState.Error(e.message)
                    _action.send(LoginAction.ShowToast(message = e.message))
                    debug("Login Exception: ${e.message}")
                }
        }
    }


    private fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }

    companion object {
        private const val LOG_TAG = "LoginViewModel"
    }
}
