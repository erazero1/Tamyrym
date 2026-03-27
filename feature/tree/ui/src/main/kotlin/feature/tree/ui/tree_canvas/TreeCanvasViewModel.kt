package feature.tree.ui.tree_canvas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onException
import core.domain.result.onSuccess
import feature.tree.domain.model.AddRelationRequest
import feature.tree.domain.model.PersonRequest
import feature.tree.domain.usecase.AddRelationToPersonUseCase
import feature.tree.domain.usecase.GetOptimizedTreeGraphUseCase
import feature.tree.ui.tree_canvas.model.TreeCanvasAction
import feature.tree.ui.tree_canvas.model.TreeCanvasEvent
import feature.tree.ui.tree_canvas.model.TreeCanvasState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreeCanvasViewModel(
    private val getOptimizedTreeGraphUseCase: GetOptimizedTreeGraphUseCase,
    private val addRelationToPersonUseCase: AddRelationToPersonUseCase,
) : ViewModel() {
    private var currentTreeId: String = ""

    private val _state: MutableStateFlow<TreeCanvasState> =
        MutableStateFlow(TreeCanvasState.Initial)
    internal val state: StateFlow<TreeCanvasState> = _state.asStateFlow()

    private val _action: Channel<TreeCanvasAction> = Channel(capacity = Channel.BUFFERED)
    internal val action: Flow<TreeCanvasAction> = _action.receiveAsFlow()

    internal fun onEvent(event: TreeCanvasEvent) {
        when (event) {
            is TreeCanvasEvent.LoadTreeGraph -> {
                currentTreeId = event.treeId
                loadTreeGraph(event.treeId, event.targetPersonId)
            }

            is TreeCanvasEvent.SelectPerson -> updateSelectedPerson(event.personId)
            is TreeCanvasEvent.AddRelative -> addRelative(event.request)
            is TreeCanvasEvent.AddFirstPerson -> addFirstPerson(event.request)
        }
    }

    private fun addFirstPerson(request: PersonRequest) {
        addRelative(
            AddRelationRequest(
                person = request,
                personIdToFocus = "",
                relationType = null
            )
        )
    }


    private fun loadTreeGraph(treeId: String, targetPersonId: String?) {
        if (_state.value is TreeCanvasState.Loading) return
        _state.update { TreeCanvasState.Loading }
        viewModelScope.launch {
            getOptimizedTreeGraphUseCase(
                treeId = treeId,
                targetPersonId = targetPersonId ?: "",
                depth = 5,
            ).onSuccess { treeGraph ->
                _state.update {
                    TreeCanvasState.Success(treeGraph)
                }
            }
        }
    }

    private fun updateSelectedPerson(personId: String?) {
        val currentState = _state.value
        if (currentState is TreeCanvasState.Success) {
            _state.update { currentState.copy(selectedPersonId = personId) }
        }
    }

    private fun addRelative(request: AddRelationRequest) {
        val currentState = _state.value
        if (currentState !is TreeCanvasState.Success) return

        _state.update { currentState.copy(isAddingRelationLoading = true) }

        viewModelScope.launch {
            addRelationToPersonUseCase(currentTreeId, request)
                .onSuccess { person ->
                    _state.update {
                        (it as? TreeCanvasState.Success)?.copy(
                            isAddingRelationLoading = false,
                            selectedPersonId = null
                        ) ?: it
                    }
                    _action.send(TreeCanvasAction.ShowSnackbar("Successfully added relation"))
                    loadTreeGraph(currentTreeId, null)
                }
                .onError { _, message, _ ->
                    _state.update {
                        (it as? TreeCanvasState.Success)?.copy(isAddingRelationLoading = false)
                            ?: it
                    }
                    _action.send(
                        TreeCanvasAction.ShowSnackbar(
                            message ?: "Failed to add relation"
                        )
                    )
                }
                .onException { exception ->
                    _state.update {
                        (it as? TreeCanvasState.Success)?.copy(isAddingRelationLoading = false)
                            ?: it
                    }
                    _action.send(
                        TreeCanvasAction.ShowSnackbar(
                            exception.message ?: "An unexpected error occurred"
                        )
                    )
                }
        }
    }
}