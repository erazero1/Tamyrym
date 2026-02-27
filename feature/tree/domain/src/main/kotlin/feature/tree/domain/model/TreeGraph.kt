package feature.tree.domain.model

data class TreeGraph(
    val links: List<Link>,
    val persons: List<PersonExt>,
    val unions: List<Union>
) {
    companion object {
        fun init() = TreeGraph(
            links = emptyList(),
            persons = emptyList(),
            unions = emptyList()
        )
    }
}