package feature.auth.domain.model

enum class Role(val value: String) {
    USER("USER"),
    ADMIN("ADMIN");

    companion object {
        fun findRole(value: String?): Role {
            return entries
                .find { role -> role.value == value }
                ?: USER
        }
    }
}