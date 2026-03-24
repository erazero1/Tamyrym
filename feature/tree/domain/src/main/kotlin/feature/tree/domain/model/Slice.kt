package feature.tree.domain.model

data class Slice(
    val depth: Int,
    val focusPersonId: String,
    val truncated: Boolean,
)