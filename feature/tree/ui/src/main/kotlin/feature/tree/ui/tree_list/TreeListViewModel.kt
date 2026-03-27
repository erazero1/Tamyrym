package feature.tree.ui.tree_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onError
import core.domain.result.onException
import core.domain.result.onSuccess
import feature.tree.domain.model.TreeRequest
import feature.tree.domain.usecase.CreateNewTreeUseCase
import feature.tree.domain.usecase.GetTreeListUseCase
import feature.tree.domain.usecase.UpdateTreeUseCase
import feature.tree.ui.tree_list.model.TreeListAction
import feature.tree.ui.tree_list.model.TreeListEvent
import feature.tree.ui.tree_list.model.TreeListState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreeListViewModel(
    private val getTreeListUseCase: GetTreeListUseCase,
    private val createNewTreeUseCase: CreateNewTreeUseCase,
    private val updateTreeUseCase: UpdateTreeUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<TreeListState> = MutableStateFlow(TreeListState.Initial)
    internal val state: StateFlow<TreeListState> = _state.asStateFlow()

    private val _action: Channel<TreeListAction> = Channel(capacity = Channel.BUFFERED)
    internal val action: Flow<TreeListAction> = _action.receiveAsFlow()

    internal fun onEvent(event: TreeListEvent) {
        when (event) {
            is TreeListEvent.CreateNewTree -> createNewTree(
                name = event.name,
                description = event.description
            )

            is TreeListEvent.LoadTreeList -> loadTreeList()
            is TreeListEvent.UpdateTree -> updateTree(
                treeId = event.treeId,
                name = event.name,
                description = event.description,
            )
        }
    }

    private fun updateTree(treeId: String, name: String, description: String) {
        val currentState = _state.value as? TreeListState.Success ?: return
        viewModelScope.launch {
            updateTreeUseCase(
                treeId = treeId,
                treeRequest = TreeRequest(
                    name = name,
                    description = description,
                )
            ).onSuccess { tree ->
                val updatedTrees = currentState.trees.map { if (it.id == tree.id) tree else it }
                _state.update {
                    currentState.copy(trees = updatedTrees)
                }
            }.onError { _, message, _ ->
                _state.update { TreeListState.Error(message) }
            }.onException { _ ->
                _state.update { TreeListState.Error() }
            }
        }
    }

    private fun createNewTree(name: String, description: String) {
        val currentState = _state.value as? TreeListState.Success ?: return
        viewModelScope.launch {
            createNewTreeUseCase(
                TreeRequest(
                    name = name,
                    description = description,
                )
            ).onSuccess { tree ->
                _state.update {
                    currentState.copy(trees = currentState.trees + tree)
                }
                _action.send(TreeListAction.ProceedToTreeCanvas(tree.id))
            }.onError { _, message, _ ->
                _state.update { TreeListState.Error(message) }
            }.onException { _ ->
                _state.update { TreeListState.Error() }
            }
        }
    }

    private fun loadTreeList() {
        if (_state.value is TreeListState.Loading) return
        _state.update { TreeListState.Loading }
        viewModelScope.launch {
            getTreeListUseCase().onSuccess { trees ->
                _state.update {
                    TreeListState.Success(trees)
                }
            }.onError { _, message, _ ->
                _state.update {
                    TreeListState.Error(message)
                }
            }.onException { _ ->
                _state.update {
                    TreeListState.Error()
                }
            }
        }
    }
}