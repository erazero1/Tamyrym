package feature.auth.domain.model

data class UserUpdate(
    val birthYear: Int,
    val firstName: String,
    val gender: Gender,
    val lastName: String,
    val pictureUrl: String,
)
