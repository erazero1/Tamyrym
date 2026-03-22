package com.erazero1.splash.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import feature.auth.domain.usecase.IsLoggedInUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SplashViewModel(
    private val isLoggedInUseCase: IsLoggedInUseCase,
) : ViewModel() {

    private val _isLoggedIn: MutableStateFlow<Boolean?> = MutableStateFlow(null)
    val isLoggedIn: StateFlow<Boolean?> = _isLoggedIn.asStateFlow()

    init {
        viewModelScope.launch {
            _isLoggedIn.update { isLoggedInUseCase() }
        }
    }
}