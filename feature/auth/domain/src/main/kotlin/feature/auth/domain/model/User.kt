package feature.auth.domain.model

data class User(
    val birthYear: Int,
    val email: String,
    val firstName: String,
    val gender: Gender,
    val id: String,
    val lastName: String,
    val role: Role,
    val userStatus: UserStatus,
)
