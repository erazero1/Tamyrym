package feature.tree.domain.model

data class TreeRequest(
    val name: String,
    val description: String,
) {
    companion object {
        fun init() = TreeRequest(
            name = "",
            description = ""
        )
    }
}