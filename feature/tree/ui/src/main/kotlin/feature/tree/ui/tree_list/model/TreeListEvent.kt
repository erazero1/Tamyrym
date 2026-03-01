package feature.tree.ui.tree_list.model

internal sealed interface TreeListEvent {
    data object LoadTreeList : TreeListEvent
    data class CreateNewTree(val name: String, val description: String) : TreeListEvent
    data class UpdateTree(val treeId: String, val name: String, val description: String) :
        TreeListEvent
}