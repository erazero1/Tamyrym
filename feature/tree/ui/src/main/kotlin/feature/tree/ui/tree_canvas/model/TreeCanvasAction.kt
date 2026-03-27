package feature.tree.ui.tree_canvas.model

internal sealed interface TreeCanvasAction {
    data class ShowSnackbar(val message: String) : TreeCanvasAction
}