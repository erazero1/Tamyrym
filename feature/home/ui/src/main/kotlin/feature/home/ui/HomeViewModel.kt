package feature.home.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onException
import core.domain.result.onSuccess
import feature.auth.domain.usecase.GetProfileUseCase
import feature.home.ui.model.HomeAction
import feature.home.ui.model.HomeEvent
import feature.home.ui.model.HomeState
import feature.home.ui.model.UpcomingBirthday
import feature.tree.domain.usecase.GetTreeListUseCase
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    private val getProfileUseCase: GetProfileUseCase,
    private val getTreeListUseCase: GetTreeListUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState(isLoading = true))
    val state: StateFlow<HomeState> = _state.asStateFlow()

    private val _action = Channel<HomeAction>(Channel.BUFFERED)
    internal val action: Flow<HomeAction> = _action.receiveAsFlow()

    init {
        onEvent(HomeEvent.LoadData)
    }

    internal fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadData -> loadDashboardData()
        }
    }

    private fun loadDashboardData() {
        if (_state.value.isLoading && _state.value.userName.isNotEmpty()) return

        _state.update { it.copy(isLoading = true) }

        viewModelScope.launch {
            getProfileUseCase()
                .onSuccess { user ->
                    _state.update { currentState ->
                        currentState.copy(
                            isLoading = false,
                            userName = user.firstName,
                            upcomingBirthdays = if (user.id == "6a1f423a-3e33-4f4c-813f-f50d7f4aa024") listOf(
                                UpcomingBirthday(
                                    personId = "cae50e36-15c4-42dd-aa34-288354bc7b9a",
                                    personName = "Ерасыл",
                                    dateText = "19 Августа",
                                    ageTurns = 20
                                )
                            ) else emptyList()
                        )
                    }
                }
                .onError { _, message, _ ->
                    _state.update { it.copy(isLoading = false) }
                    _action.send(HomeAction.ShowToast(message ?: "Ошибка загрузки профиля"))
                }
                .onException { _ ->
                    _state.update { it.copy(isLoading = false) }
                    _action.send(HomeAction.ShowToast("Неизвестная ошибка"))
                }
            getTreeListUseCase().onSuccess { trees ->
                _state.update { currentState ->
                    currentState.copy(
                        isLoading = false,
                        recentTrees = trees.sortedBy { it.updatedAt },
                    )
                }
            }
        }
    }
}