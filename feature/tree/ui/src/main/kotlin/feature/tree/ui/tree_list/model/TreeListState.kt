package feature.tree.ui.tree_list.model

import feature.tree.domain.model.Tree

internal sealed class TreeListState {
    data object Initial : TreeListState()
    data object Loading : TreeListState()
    data class Error(val message: String? = null) : TreeListState()
    data class Success(val trees: List<Tree>, val isAnalyzing: Boolean = false) : TreeListState()
}