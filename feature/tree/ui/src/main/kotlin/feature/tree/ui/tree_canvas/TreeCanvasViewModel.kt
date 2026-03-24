package feature.tree.ui.tree_canvas

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import core.domain.result.onSuccess
import feature.tree.domain.usecase.GetOptimizedTreeGraphUseCase
import feature.tree.ui.tree_canvas.model.TreeCanvasEvent
import feature.tree.ui.tree_canvas.model.TreeCanvasState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class TreeCanvasViewModel(
    private val getOptimizedTreeGraphUseCase: GetOptimizedTreeGraphUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<TreeCanvasState> =
        MutableStateFlow(TreeCanvasState.Initial)
    internal val state: StateFlow<TreeCanvasState> = _state.asStateFlow()

    internal fun onEvent(event: TreeCanvasEvent) {
        when (event) {
            is TreeCanvasEvent.LoadTreeGraph -> loadTreeGraph(event.treeId, event.targetPersonId)
        }
    }

    private fun loadTreeGraph(treeId: String, targetPersonId: String?) {
        viewModelScope.launch {
            getOptimizedTreeGraphUseCase(
                treeId = treeId,
                targetPersonId = targetPersonId ?: "",
                depth = 2,
            ).onSuccess { treeGraph ->
                _state.update {
                    TreeCanvasState.Success(treeGraph)
                }
            }
        }
    }
}