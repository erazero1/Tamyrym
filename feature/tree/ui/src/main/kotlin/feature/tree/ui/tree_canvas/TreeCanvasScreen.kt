package feature.tree.ui.tree_canvas

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme
import core.ui.uikit.components.ErrorCard
import core.ui.uikit.components.LoadingCard
import core.ui.uikit.effects.SingleEventEffect
import feature.tree.domain.model.AddRelationRequest
import feature.tree.domain.model.TreeGraph
import feature.tree.ui.tree_canvas.components.AddRelativeBottomSheet
import feature.tree.ui.tree_canvas.components.EmptyTreeState
import feature.tree.ui.tree_canvas.components.PersonCard
import feature.tree.ui.tree_canvas.components.ZoomableCanvas
import feature.tree.ui.tree_canvas.components.drawFamilyLines
import feature.tree.ui.tree_canvas.model.LayoutConfig
import feature.tree.ui.tree_canvas.model.LayoutResult
import feature.tree.ui.tree_canvas.model.TreeCanvasAction
import feature.tree.ui.tree_canvas.model.TreeCanvasEvent
import feature.tree.ui.tree_canvas.model.TreeCanvasState
import org.koin.androidx.compose.koinViewModel
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TreeCanvasScreen(
    modifier: Modifier = Modifier,
    treeId: String,
    onPersonClick: (String) -> Unit,
) {
    val viewModel: TreeCanvasViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showAddFirstPersonSheet by remember { mutableStateOf(false) }

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            is TreeCanvasAction.ShowSnackbar -> {
                snackbarHostState.showSnackbar(action.message)
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(TreeCanvasEvent.LoadTreeGraph(treeId))
    }

    Box(modifier = modifier.fillMaxSize()) {
        TreeCanvasLayout(
            modifier = Modifier.fillMaxSize(),
            state = state,
            treeId = treeId,
            onPersonClick = { personId ->
                onPersonClick(personId)
            },
            onCardLongClick = { personId ->
                viewModel.onEvent(TreeCanvasEvent.SelectPerson(personId))
            },
            onAddFirstPersonClick = {
                showAddFirstPersonSheet = true
            },
            onEvent = viewModel::onEvent,
        )

        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
    val successState = state as? TreeCanvasState.Success
    val isAddingRelative = successState?.selectedPersonId != null
    val isAddingFirstPerson = showAddFirstPersonSheet
    if (isAddingRelative || isAddingFirstPerson) {
        val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
        ModalBottomSheet(
            onDismissRequest = {
                if (isAddingRelative) viewModel.onEvent(TreeCanvasEvent.SelectPerson(null))
                if (isAddingFirstPerson) showAddFirstPersonSheet = false
            },
            sheetState = sheetState,
        ) {
            AddRelativeBottomSheet(
                isLoading = successState?.isAddingRelationLoading == true,
                isFirstPerson = isAddingFirstPerson,
                onSave = { person, relationType ->
                    if (isAddingFirstPerson) {
                        viewModel.onEvent(TreeCanvasEvent.AddFirstPerson(person))
                        showAddFirstPersonSheet = false
                    } else {
                        val request = AddRelationRequest(
                            person = person,
                            personIdToFocus = successState!!.selectedPersonId!!,
                            relationType = relationType!!
                        )
                        viewModel.onEvent(TreeCanvasEvent.AddRelative(request))
                    }
                }
            )
        }
    }
}

@Composable
private fun TreeCanvasLayout(
    modifier: Modifier = Modifier,
    state: TreeCanvasState,
    treeId: String,
    onPersonClick: (String) -> Unit,
    onCardLongClick: (String) -> Unit,
    onAddFirstPersonClick: () -> Unit,
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

        is TreeCanvasState.Success -> {
            if (state.treeGraph.persons.isEmpty()) {
                EmptyTreeState(
                    modifier = modifier.fillMaxSize(),
                    onAddFirstPersonClick = onAddFirstPersonClick
                )
            } else {
                TreeCanvasContent(
                    modifier = modifier,
                    tree = state.treeGraph,
                    layoutResult = state.layoutResult,
                    onPersonClick = onPersonClick,
                    onCardLongClick = onCardLongClick,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
private fun TreeCanvasContent(
    modifier: Modifier = Modifier,
    tree: TreeGraph,
    layoutResult: LayoutResult?,
    config: LayoutConfig = LayoutConfig(),
    onPersonClick: (String) -> Unit,
    onCardLongClick: (String) -> Unit,
    onEvent: (TreeCanvasEvent) -> Unit,
) {
    val density = LocalDensity.current
    val pixelConfig = remember(config, density) {
        with(density) {
            LayoutConfig(
                nodeWidth = config.nodeWidth.dp.toPx(),
                nodeHeight = config.nodeHeight.dp.toPx(),
                horizontalSpacing = config.horizontalSpacing.dp.toPx(),
                verticalSpacing = config.verticalSpacing.dp.toPx(),
                spouseSpacing = config.spouseSpacing.dp.toPx()
            )
        }
    }

    // Update layout config in ViewModel when pixelConfig changes
    LaunchedEffect(pixelConfig) {
        onEvent(TreeCanvasEvent.UpdateLayoutConfig(pixelConfig))
    }

    if (layoutResult == null) {
        LoadingCard(modifier = Modifier.fillMaxSize())
        return
    }

    val positions = layoutResult.positions
    val cyclicSet = layoutResult.cyclicLinks

    ZoomableCanvas(
        modifier = modifier
            .fillMaxSize()
            .background(AppTheme.colors.surfaceBright)
    ) {
        // ── Viewport Culling ─────────────────────────────────────────────
        val viewportRect = remember(scale, offset, canvasSize) {
            if (canvasSize == IntSize.Zero) return@remember Rect.Zero
            Rect(
                left = -offset.x / scale,
                top = -offset.y / scale,
                right = (-offset.x + canvasSize.width) / scale,
                bottom = (-offset.y + canvasSize.height) / scale
            ).inflate(200f) // Add margin to avoid flickering at edges
        }

        Box(
            modifier = Modifier
                .graphicsLayer(
                    scaleX = scale,
                    scaleY = scale,
                    translationX = offset.x,
                    translationY = offset.y,
                    transformOrigin = TransformOrigin(0f, 0f)
                )
                .fillMaxSize()
        ) {
            val colorLine = AppTheme.colors.outline
            val colorUnionDot = AppTheme.colors.tertiary
            val colorCyclic = AppTheme.colors.menuIconRed

            // ── Layer 1: relationship lines ──────────────────────────────
            Canvas(
                modifier = Modifier
                    .fillMaxSize()
                    .drawWithCache {
                        onDrawBehind {
                            // Spouse connector lines
                            tree.unions.forEach { union ->
                                if (union.person2Id.isEmpty()) return@forEach
                                val p1Pos = positions[union.person1Id] ?: return@forEach
                                val p2Pos = positions[union.person2Id] ?: return@forEach

                                val midY = p1Pos.y + pixelConfig.nodeHeight / 2f
                                val startX = p1Pos.x + pixelConfig.nodeWidth
                                val endX = p2Pos.x

                                // Culling for lines
                                val lineRect = Rect(startX, midY - 10f, endX, midY + 10f)
                                if (!viewportRect.overlaps(lineRect)) return@forEach

                                drawLine(
                                    color = colorLine,
                                    start = Offset(startX, midY),
                                    end = Offset(endX, midY),
                                    strokeWidth = 2f
                                )

                                // Union dot
                                val unionPos = positions[union.id]
                                if (unionPos != null) {
                                    drawCircle(
                                        color = colorUnionDot,
                                        radius = 5f,
                                        center = Offset((startX + endX) / 2f, midY)
                                    )
                                }
                            }

                            // Parent-Child lines
                            tree.unions.forEach { union ->
                                val unionPos = positions[union.id] ?: return@forEach
                                if (union.childrenIds.isEmpty()) return@forEach

                                val isCyclic = union.id in cyclicSet

                                val childTops = union.childrenIds.mapNotNull { childId ->
                                    positions[childId]?.let { pos ->
                                        val topCenter =
                                            Offset(pos.x + pixelConfig.nodeWidth / 2f, pos.y)
                                        // Optimization: we could filter individual children here,
                                        // but it's more complex for the path drawing logic.
                                        topCenter
                                    }
                                }
                                if (childTops.isEmpty()) return@forEach

                                drawFamilyLines(
                                    parentAnchor = unionPos,
                                    childTops = childTops,
                                    config = pixelConfig,
                                    isCyclic = isCyclic,
                                    color = if (isCyclic) colorCyclic else colorLine
                                )
                            }
                        }
                    }
            ) {}

            // ── Layer 2: person cards ────────────────────────────────────
            tree.persons.forEach { person ->
                val pos = positions[person.id] ?: return@forEach
                val cardRect = Rect(
                    pos.x,
                    pos.y,
                    pos.x + pixelConfig.nodeWidth,
                    pos.y + pixelConfig.nodeHeight
                )

                if (viewportRect.overlaps(cardRect)) {
                    PersonCard(
                        person = person,
                        config = pixelConfig,
                        scale = scale, // Pass scale for LOD
                        modifier = Modifier.offset {
                            IntOffset(pos.x.roundToInt(), pos.y.roundToInt())
                        },
                        onCardClick = { personId ->
                            onPersonClick(personId)
                        },
                        onCardLongClick = onCardLongClick,
                    )
                }
            }
        }
    }
}
