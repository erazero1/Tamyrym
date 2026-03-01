package feature.tree.ui.tree_canvas

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import core.presentation.R
import core.ui.uikit.components.ErrorCard
import core.ui.uikit.components.LoadingCard
import feature.tree.domain.model.TreeGraph
import feature.tree.ui.tree_canvas.components.DrawConnections
import feature.tree.ui.tree_canvas.components.PersonCard
import feature.tree.ui.tree_canvas.components.ZoomableTreeCanvas
import feature.tree.ui.tree_canvas.model.LayoutEngine
import feature.tree.ui.tree_canvas.model.TreeCanvasEvent
import feature.tree.ui.tree_canvas.model.TreeCanvasState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TreeCanvasScreen(
    modifier: Modifier = Modifier,
    treeId: String,
) {
    val viewModel: TreeCanvasViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(TreeCanvasEvent.LoadTreeGraph(treeId))
    }

    TreeCanvasLayout(
        modifier = modifier,
        state = state.value,
        treeId = treeId,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun TreeCanvasLayout(
    modifier: Modifier = Modifier,
    state: TreeCanvasState,
    treeId: String,
    onEvent: (TreeCanvasEvent) -> Unit,
) {
    when (state) {
        is TreeCanvasState.Initial -> Unit
        is TreeCanvasState.Loading -> LoadingCard(modifier = Modifier.fillMaxSize())
        is TreeCanvasState.Error -> ErrorCard(
            modifier = Modifier.fillMaxSize(),
            message = state.message ?: stringResource(R.string.unknown_error),
            onTryAgainClick = { onEvent(TreeCanvasEvent.LoadTreeGraph(treeId)) }
        )

        is TreeCanvasState.Success -> TreeCanvasContent(
            modifier = modifier,
            treeGraph = state.treeGraph,
            onEvent = onEvent,
        )
    }
}

@Composable
private fun TreeCanvasContent(
    modifier: Modifier = Modifier,
    treeGraph: TreeGraph,
    onEvent: (TreeCanvasEvent) -> Unit,
) {
    val uiNodesMap = remember(treeGraph) {
        val engine = LayoutEngine()
        engine.processGraph(treeGraph)
    }

    ZoomableTreeCanvas {
        DrawConnections(
            links = treeGraph.links,
            unions = treeGraph.unions,
            uiNodes = uiNodesMap,
            nodeWidth = 300f,
            nodeHeight = 150f
        )

        uiNodesMap.values.forEach { uiNode ->
            PersonCard(
                personExt = uiNode.person,
                modifier = Modifier
                    .offset {
                        IntOffset(uiNode.x.toInt() * 2, uiNode.y.toInt())
                    }
            )
        }
    }
}