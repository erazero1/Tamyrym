package feature.profile.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onException
import core.domain.result.onSuccess
import feature.auth.domain.usecase.GetProfileUseCase
import feature.auth.domain.usecase.LogoutUseCase
import feature.profile.ui.profile.model.ProfileAction
import feature.profile.ui.profile.model.ProfileEvent
import feature.profile.ui.profile.model.ProfileState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val onLogoutUseCase: LogoutUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<ProfileState>(ProfileState.Initial)
    internal val state = _state.asStateFlow()

    private val _action = Channel<ProfileAction>(Channel.BUFFERED)
    val action: Flow<ProfileAction> = _action.receiveAsFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            ProfileEvent.LoadProfile -> loadProfile()
            ProfileEvent.OnLogoutClick -> onLogout()
        }
    }

    private fun loadProfile() {
        debug("loadProfile() method was called.")
        if (_state.value is ProfileState.Loading) return
        _state.update { ProfileState.Loading }
        debug("Profile State is Loading")
        viewModelScope.launch {
            getProfileUseCase()
                .onSuccess { user ->
                    _state.update { ProfileState.Success(user) }
                    debug("Profile State is Success")
                }
                .onError { _, message, _ ->
                    _state.update { ProfileState.Error(message) }
                    debug("Profile State is Error: $message")
                }
                .onException { _ ->
                    _state.update { ProfileState.Error() }
                    debug("Profile State is Error")
                }
        }
    }

    private fun onLogout() {
        viewModelScope.launch {
            onLogoutUseCase().onSuccess {
                _action.send(ProfileAction.OnLogoutClick)
            }.onError { _, _, _ ->
                _action.send(ProfileAction.ShowToast())
            }.onException { _ ->
                _action.send(ProfileAction.ShowToast())
            }
        }
    }

    private fun debug(message: String) {
        Log.d(LOG_TAG, message)
    }

    companion object {
        private const val LOG_TAG = "ProfileViewModel"
    }
}
