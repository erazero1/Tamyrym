package feature.auth.domain.model

import core.domain.model.Gender

data class UserUpdate(
    val birthYear: Int,
    val firstName: String,
    val gender: Gender,
    val lastName: String,
)
