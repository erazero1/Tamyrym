package feature.tree.ui.tree_list.model

internal sealed interface TreeListEvent {
    data object LoadTreeList : TreeListEvent
    data object CreateNewTree : TreeListEvent
}