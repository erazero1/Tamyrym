package feature.auth.domain.model

enum class Gender(val value: String) {
    MALE("MALE"),
    FEMALE("FEMALE"),
    OTHER("OTHER");

    companion object {
        fun findValue(value: String?): Gender {
            return entries
                .find { gender -> gender.value == value }
                ?: OTHER
        }
    }
}