package feature.tree.ui.tree_canvas.model

internal sealed interface TreeCanvasEvent {
    data class LoadTreeGraph(val treeId: String, val targetPersonId: String? = null) :
        TreeCanvasEvent
}