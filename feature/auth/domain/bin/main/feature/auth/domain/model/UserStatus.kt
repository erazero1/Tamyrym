package feature.auth.domain.model

enum class UserStatus(val value: String) {
    ACTIVE("ACTIVE"),
    BANNED("BANNED");

    companion object {
        fun findStatus(value: String?): UserStatus {
            return entries.find { userStatus -> userStatus.value == value }
                ?: ACTIVE
        }
    }
}