package feature.tree.domain.model

import core.domain.model.UnionType

data class Union(
    val id: String,
    val type: UnionType,
    val generation: Int,
    val isVirtual: Boolean,
    val person1Id: String,
    val person2Id: String,
    val childrenIds: List<String>,
)