package feature.tree.ui.person_detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onException
import core.domain.result.onSuccess
import feature.tree.domain.usecase.GetPersonUseCase
import feature.tree.ui.person_detail.model.PersonDetailAction
import feature.tree.ui.person_detail.model.PersonDetailEvent
import feature.tree.ui.person_detail.model.PersonDetailState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PersonDetailViewModel(
    private val getPersonUseCase: GetPersonUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<PersonDetailState> =
        MutableStateFlow(PersonDetailState.Initial)
    internal val state: StateFlow<PersonDetailState> = _state.asStateFlow()

    private val _action: Channel<PersonDetailAction> = Channel(capacity = Channel.BUFFERED)
    internal val action: Flow<PersonDetailAction> = _action.receiveAsFlow()

    internal fun onEvent(event: PersonDetailEvent) {
        when (event) {
            is PersonDetailEvent.LoadPerson -> loadPerson(event.personId)
        }
    }

    private fun loadPerson(personId: String) {
        if (_state.value is PersonDetailState.Loading) return

        viewModelScope.launch {
            getPersonUseCase(personId = personId).onSuccess { person ->
                _state.update {
                    PersonDetailState.Success(person = person)
                }
            }.onError { _, message, _ ->
                _state.update { PersonDetailState.Error(message) }
            }.onException { _ ->
                _state.update { PersonDetailState.Error() }
            }
        }
    }
}