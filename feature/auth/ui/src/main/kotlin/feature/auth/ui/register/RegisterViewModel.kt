package feature.auth.ui.register

import androidx.lifecycle.ViewModel
import feature.auth.domain.model.UserRegistrationInfo
import feature.auth.domain.usecase.RegisterUseCase
import kotlinx.coroutines.flow.MutableStateFlow

class RegisterViewModel(
    private val registerUseCase: RegisterUseCase,
) : ViewModel() {
    // TODO
}