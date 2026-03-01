package feature.tree.ui.tree_canvas.model

interface TreeCanvasEvent {
    data class LoadTreeGraph(val treeId: String, val targetPersonId: String? = null) :
        TreeCanvasEvent
}