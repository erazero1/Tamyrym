package feature.auth.domain.model

data class UserRegistrationInfo(
    val birthYear: Int?,
    val email: String,
    val firstName: String,
    val gender: String?,
    val lastName: String?,
    val password: String,
)
