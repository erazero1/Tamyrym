package feature.tree.domain.model

import core.domain.model.Gender

data class PersonInfo(
    val id: String,
    val firstName: String,
    val lastName: String,
    val gender: Gender,
    val generation: Int,
    val mainUnionId: String,
)