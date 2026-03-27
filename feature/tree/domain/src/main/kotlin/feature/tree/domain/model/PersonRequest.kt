package feature.tree.domain.model

import core.domain.model.Gender

data class PersonRequest(
    val biography: String,
    val birthDate: String,
    val birthPlace: String,
    val deathDate: String,
    val deathPlace: String,
    val firstName: String,
    val gender: Gender,
    val isAlive: Boolean,
    val lastName: String,
    val maidenName: String,
    val patronymic: String,
    val photoUrl: String,
) {
    companion object {
        fun init() = PersonRequest(
            biography = "",
            birthDate = "",
            birthPlace = "",
            deathDate = "",
            deathPlace = "",
            firstName = "",
            gender = Gender.OTHER,
            isAlive = true,
            lastName = "",
            maidenName = "",
            patronymic = "",
            photoUrl = ""
        )
    }
}