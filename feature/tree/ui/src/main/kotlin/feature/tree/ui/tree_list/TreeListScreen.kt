package feature.tree.ui.tree_list

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import core.domain.model.Tree
import core.presentation.R
import core.ui.theme.AppTheme
import core.ui.uikit.components.ErrorCard
import core.ui.uikit.components.LoadingCard
import core.ui.uikit.effects.SingleEventEffect
import core.ui.utils.showLongToast
import feature.tree.domain.model.ai.TreeAnalysisResult
import feature.tree.ui.tree_list.components.AnalysisResultDialog
import feature.tree.ui.tree_list.components.TreeCard
import feature.tree.ui.tree_list.components.TreeCreationDialog
import feature.tree.ui.tree_list.model.TreeDialogState
import feature.tree.ui.tree_list.model.TreeListAction
import feature.tree.ui.tree_list.model.TreeListEvent
import feature.tree.ui.tree_list.model.TreeListState
import org.koin.androidx.compose.koinViewModel

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
internal fun TreeListScreen(
    modifier: Modifier = Modifier,
    onNavigateToTree: (treeId: String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: TreeListViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()
    var dialogState by remember { mutableStateOf<TreeDialogState?>(null) }
    var analysisResultState by remember { mutableStateOf<TreeAnalysisResult?>(null) }

    LaunchedEffect(Unit) {
        viewModel.onEvent(TreeListEvent.LoadTreeList)
    }

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            is TreeListAction.ProceedToTreeCanvas -> {
                onNavigateToTree(action.treeId)
            }

            is TreeListAction.ShowToast -> {
                showLongToast(context, action.message ?: context.getString(R.string.unknown_error))
            }

            is TreeListAction.ShowAnalysisResult -> analysisResultState = action.result
        }
    }

    dialogState?.let { state ->
        TreeCreationDialog(
            modifier = Modifier,
            title = when (state) {
                is TreeDialogState.Create -> stringResource(R.string.create_new_tree)
                is TreeDialogState.Edit -> stringResource(R.string.edit)
            },
            initialName = when (state) {
                is TreeDialogState.Create -> ""
                is TreeDialogState.Edit -> state.currentName
            },
            initialDescription = when (state) {
                is TreeDialogState.Create -> ""
                is TreeDialogState.Edit -> state.currentDescription
            },
            onDismiss = { dialogState = null },
            onConfirm = { name, description ->
                when (state) {
                    is TreeDialogState.Create -> {
                        viewModel.onEvent(TreeListEvent.CreateNewTree(name, description))
                    }

                    is TreeDialogState.Edit -> {
                        viewModel.onEvent(
                            TreeListEvent.UpdateTree(
                                treeId = state.treeId,
                                name = name,
                                description = description
                            )
                        )
                    }
                }
            }
        )
    }

    analysisResultState?.let { result ->
        AnalysisResultDialog(
            result = result,
            onDismiss = { analysisResultState = null }
        )
    }

    TreeListLayout(
        modifier = modifier,
        state = state.value,
        onNavigateToTree = onNavigateToTree,
        onCreateNewTree = {
            dialogState = TreeDialogState.Create
        },
        onEditTree = { treeId, initialName, initialDescription ->
            dialogState = TreeDialogState.Edit(treeId, initialName, initialDescription)
        },
        onAnalyzeTree = { treeId ->
            viewModel.onEvent(TreeListEvent.AnalyzeTree(treeId))
        },
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun TreeListLayout(
    modifier: Modifier = Modifier,
    state: TreeListState,
    onCreateNewTree: () -> Unit,
    onEditTree: (String, String, String) -> Unit,
    onNavigateToTree: (treeId: String) -> Unit,
    onAnalyzeTree: (String) -> Unit,
    onEvent: (TreeListEvent) -> Unit,
) {
    when (state) {
        is TreeListState.Initial -> Unit
        is TreeListState.Loading -> LoadingCard(modifier = Modifier.fillMaxSize())
        is TreeListState.Error -> ErrorCard(
            modifier = Modifier.fillMaxSize(),
            message = state.message ?: stringResource(R.string.unknown_error),
            onTryAgainClick = { onEvent(TreeListEvent.LoadTreeList) }
        )

        is TreeListState.Success -> TreeListContent(
            modifier = modifier,
            trees = state.trees,
            onCreateNewTree = onCreateNewTree,
            onEditTree = onEditTree,
            onAnalyzeTree = onAnalyzeTree,
            onNavigateToTree = onNavigateToTree,
        )
    }
}

@Composable
private fun TreeListContent(
    modifier: Modifier = Modifier,
    trees: List<Tree>,
    onCreateNewTree: () -> Unit,
    onEditTree: (treeId: String, currentName: String, currentDescription: String) -> Unit,
    onAnalyzeTree: (String) -> Unit,
    onNavigateToTree: (treeId: String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxWidth(),
        containerColor = AppTheme.colors.surfaceBright,
        contentColor = AppTheme.colors.onSurface,
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onCreateNewTree()
                },
                containerColor = AppTheme.colors.primary,
            ) {
                Icon(
                    painter = painterResource(R.drawable.add_24px),
                    contentDescription = stringResource(R.string.create_new_tree),
                    tint = AppTheme.colors.onPrimary
                )
            }
        },
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            items(trees) { tree ->
                TreeCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    tree = tree,
                    onClick = { onNavigateToTree(tree.id) },
                    onEditClick = {
                        onEditTree(
                            tree.id,
                            tree.name,
                            tree.description,
                        )
                    },
                    onAnalyzeClick = onAnalyzeTree
                )
            }
        }
    }
}