package feature.tree.ui.tree_list

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import feature.tree.ui.tree_list.model.TreeListEvent
import feature.tree.ui.tree_list.model.TreeListState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TreeListScreen(
    modifier: Modifier = Modifier,
    onNavigateToTree: (treeId: String) -> Unit,
) {
    val viewModel: TreeListViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(TreeListEvent.LoadTreeList)
    }

    TreeListLayout(
        state = state.value,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun TreeListLayout(
    modifier: Modifier = Modifier,
    state: TreeListState,
    onEvent: (TreeListEvent) -> Unit,
) {
    TODO()
}