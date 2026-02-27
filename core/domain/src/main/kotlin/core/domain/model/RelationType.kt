package core.domain.model

enum class RelationType(val value: String) {
    BROTHER("BROTHER"),
    SISTER("SISTER"),
    MOTHER("MOTHER"),
    FATHER("FATHER"),
    PARTNER("PARTNER"),
    SON("SON"),
    DAUGHTER("DAUGHTER");

    companion object {
        fun findValue(value: String?): RelationType {
            return entries
                .find { relationType -> relationType.value == value }
                ?: BROTHER
        }
    }
}