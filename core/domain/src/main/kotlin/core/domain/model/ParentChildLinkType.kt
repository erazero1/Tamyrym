package core.domain.model

enum class ParentChildLinkType(val value: String) {
    BIOLOGICAL("BIOLOGICAL"),
    ADOPTIVE("ADOPTIVE"),
    FOSTER("FOSTER");

    companion object {
        fun findValue(value: String?): ParentChildLinkType {
            return entries
                .find { parentChildLinkType -> parentChildLinkType.value == value }
                ?: BIOLOGICAL
        }
    }
}