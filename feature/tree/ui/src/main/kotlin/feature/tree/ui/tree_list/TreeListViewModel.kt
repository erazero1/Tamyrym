package feature.tree.ui.tree_list

import androidx.lifecycle.ViewModel
import feature.tree.domain.usecase.CreateNewTreeUseCase
import feature.tree.domain.usecase.GetTreeListUseCase
import feature.tree.ui.tree_list.model.TreeListEvent
import feature.tree.ui.tree_list.model.TreeListState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class TreeListViewModel(
    private val getTreeListUseCase: GetTreeListUseCase,
    private val createNewTreeUseCase: CreateNewTreeUseCase,
) : ViewModel() {
    private val _state: MutableStateFlow<TreeListState> = MutableStateFlow(TreeListState.Initial)
    internal val state: StateFlow<TreeListState> = _state.asStateFlow()
    internal fun onEvent(event: TreeListEvent) {
        when (event) {
            TreeListEvent.CreateNewTree -> TODO()
            TreeListEvent.LoadTreeList -> TODO()
        }
    }
}