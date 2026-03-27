package feature.tree.ui.tree_list.model

import feature.tree.domain.model.ai.TreeAnalysisResult

internal sealed interface TreeListAction {
    data class ProceedToTreeCanvas(val treeId: String) : TreeListAction
    data class ShowToast(val message: String? = null) : TreeListAction
    data class ShowAnalysisResult(val result: TreeAnalysisResult) : TreeListAction
}