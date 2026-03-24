package feature.tree.domain.model
data class TreeGraph(
    val persons: List<PersonInfo>,
    val rootPersonIds: List<String>,
    val slice: Slice?,
    val unions: List<Union>,
)