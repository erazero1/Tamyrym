package feature.auth.ui.auth_options

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.auth.ui.auth_options.model.AuthOptionsAction
import feature.auth.ui.auth_options.model.AuthOptionsEvent
import feature.auth.ui.auth_options.model.AuthOptionsState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class AuthOptionsViewModel : ViewModel() {
    private val _state = MutableStateFlow<AuthOptionsState>(AuthOptionsState.Idle)
    val state = _state.asStateFlow()

    private val _action = Channel<AuthOptionsAction>(Channel.BUFFERED)
    val action: Flow<AuthOptionsAction> = _action.receiveAsFlow()

    fun onEvent(event: AuthOptionsEvent) {
        viewModelScope.launch {
            when (event) {
                AuthOptionsEvent.OnGoogleSignInClick -> _action.send(AuthOptionsAction.HandleGoogleSignIn)
                AuthOptionsEvent.OnLoginClick -> _action.send(AuthOptionsAction.NavigateToLogin)
                AuthOptionsEvent.OnRegisterClick -> _action.send(AuthOptionsAction.NavigateToRegister)
            }
        }
    }
}
