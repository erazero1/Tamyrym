package feature.tree.ui.tree_list.model

internal sealed interface TreeListAction {
    data class ProceedToTreeCanvas(val treeId: String) : TreeListAction
    data class ShowToast(val message: String? = null) : TreeListAction
}