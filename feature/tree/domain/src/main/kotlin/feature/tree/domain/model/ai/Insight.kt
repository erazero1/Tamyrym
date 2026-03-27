package feature.tree.domain.model.ai

data class Insight(
    val title: String,
    val description: String,
    val type: String,
    val relatedIds: List<String>,
)