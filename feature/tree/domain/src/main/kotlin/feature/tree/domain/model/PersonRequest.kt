package feature.tree.domain.model

import core.domain.model.Gender
import java.time.Instant

data class PersonRequest(
    val biography: String,
    val birthDate: Instant,
    val birthDateString: String,
    val birthPlace: String,
    val deathDate: Instant,
    val deathDateString: String,
    val deathPlace: String,
    val firstName: String,
    val gender: Gender,
    val isAlive: Boolean,
    val lastName: String,
    val maidenName: String,
    val patronymic: String,
    val photoUrl: String
) {
    companion object {
        fun init() = PersonRequest(
            biography = "",
            birthDate = Instant.now(),
            birthDateString = "",
            birthPlace = "",
            deathDate = Instant.now(),
            deathDateString = "",
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