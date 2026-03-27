package feature.tree.domain.model.ai

data class TreeAnalysisResult(
    val treeId: String,
    val insights: List<Insight>,
)