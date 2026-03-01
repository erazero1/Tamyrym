package feature.tree.ui.tree_list.model

internal sealed class TreeDialogState {
    data object Create : TreeDialogState()
    data class Edit(
        val treeId: String,
        val currentName: String,
        val currentDescription: String,
    ) : TreeDialogState()
}