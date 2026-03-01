package feature.tree.ui.tree_canvas.model

import feature.tree.domain.model.TreeGraph

internal sealed class TreeCanvasState {
    data object Initial : TreeCanvasState()
    data object Loading : TreeCanvasState()
    data class Error(val message: String? = null) : TreeCanvasState()
    data class Success(val treeGraph: TreeGraph) : TreeCanvasState()
}