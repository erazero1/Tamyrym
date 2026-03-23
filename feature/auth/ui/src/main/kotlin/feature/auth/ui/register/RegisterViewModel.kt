package feature.auth.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onSuccess
import feature.auth.domain.model.UserRegistrationInfo
import feature.auth.domain.usecase.RegisterUseCase
import feature.auth.ui.register.model.RegisterAction
import feature.auth.ui.register.model.RegisterEvent
import feature.auth.ui.register.model.RegisterState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow<RegisterState>(RegisterState.Content())
    internal val state = _state.asStateFlow()

    private val _action = Channel<RegisterAction>()
    internal val action = _action.receiveAsFlow()

    internal fun onEvent(event: RegisterEvent) {
        when (event) {
            is RegisterEvent.OnFirstNameChanged -> updateContentState { it.copy(firstName = event.value) }
            is RegisterEvent.OnLastNameChanged -> updateContentState { it.copy(lastName = event.value) }
            is RegisterEvent.OnGenderChanged -> updateContentState { it.copy(gender = event.value) }
            is RegisterEvent.OnBirthYearSelected -> updateContentState { it.copy(birthYear = event.value) }
            is RegisterEvent.OnEmailChanged -> updateContentState {
                it.copy(
                    email = event.value,
                    emailError = !android.util.Patterns.EMAIL_ADDRESS.matcher(event.value).matches()
                )
            }

            is RegisterEvent.OnPasswordChanged -> updateContentState {
                it.copy(
                    password = event.value,
                    passwordError = !isValidPassword(event.value)
                )
            }

            RegisterEvent.OnPasswordVisibilityToggle -> updateContentState {
                it.copy(
                    isPasswordVisible = !it.isPasswordVisible
                )
            }

            RegisterEvent.OnContinueClick -> handleContinueClick()
            RegisterEvent.OnBackClick -> updateContentState { it.copy(currentStep = 1) }
            RegisterEvent.OnCompleteRegistrationClick -> handleCompleteRegistration()
        }
    }

    private fun isValidPassword(password: String): Boolean {
        val hasMinLength = password.length >= 8
        val hasDigit = password.any { it.isDigit() }
        val hasUpperCase = password.any { it.isUpperCase() }
        return hasMinLength && hasDigit && hasUpperCase
    }

    private fun handleContinueClick() {
        val currentState = state.value as? RegisterState.Content ?: return
        if (currentState.firstName.isNotBlank() && currentState.lastName.isNotBlank()) {
            updateContentState { it.copy(currentStep = 2) }
        } else {
            viewModelScope.launch {
                _action.send(RegisterAction.NotAllFieldsFilled)
            }
        }
    }

    private fun handleCompleteRegistration() {
        val currentState = state.value as? RegisterState.Content ?: return

        viewModelScope.launch {
            _state.value = RegisterState.Loading
            val userInfo = UserRegistrationInfo(
                firstName = currentState.firstName,
                lastName = currentState.lastName,
                gender = currentState.gender.name,
                birthYear = currentState.birthYear,
                email = currentState.email,
                password = currentState.password
            )
            registerUseCase(userInfo).onSuccess {
                _state.value = RegisterState.Success
                _action.send(RegisterAction.RegistrationSuccess)
            }.onError { _, message, _ ->
                _state.value = currentState
                _action.send(RegisterAction.ShowError(message))
            }
        }
    }


    private fun updateContentState(update: (RegisterState.Content) -> RegisterState.Content) {
        _state.update {
            (it as? RegisterState.Content)?.let(update) ?: it
        }
    }
}
