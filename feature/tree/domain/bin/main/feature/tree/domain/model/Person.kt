package feature.tree.domain.model

import core.domain.model.Gender
import java.time.Instant

data class Person(
    val biography: String,
    val birthDate: Instant,
    val birthDateString: String,
    val birthPlace: String,
    val createdAt: Instant,
    val createdBy: String,
    val deathDate: Instant,
    val deathDateString: String,
    val deathPlace: String,
    val firstName: String,
    val gender: Gender,
    val id: String,
    val isAlive: Boolean,
    val lastName: String,
    val maidenName: String,
    val patronymic: String,
    val photoUrl: String,
    val treeId: String,
    val updatedAt: Instant,
) {
    companion object {
        fun init() = Person(
            biography = "",
            birthDate = Instant.now(),
            birthDateString = "",
            birthPlace = "",
            createdAt = Instant.now(),
            createdBy = "",
            deathDate = Instant.now(),
            deathDateString = "",
            deathPlace = "",
            firstName = "",
            gender = Gender.OTHER,
            id = "",
            isAlive = true,
            lastName = "",
            maidenName = "",
            patronymic = "",
            photoUrl = "",
            treeId = "",
            updatedAt = Instant.now()
        )
    }
}