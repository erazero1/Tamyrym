package core.domain.model

enum class UnionType(val value: String) {
    MARRIAGE("MARRIAGE"),
    CIVIL_UNION("CIVIL_UNION"),
    DIVORCED("DIVORCED"),
    SIBLING("SIBLING"),
    OTHER("OTHER");

    companion object {
        fun findValue(value: String?): UnionType {
            return entries
                .find { unionType -> unionType.value == value }
                ?: OTHER
        }
    }
}