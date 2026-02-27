package feature.tree.domain.model

import core.domain.model.ParentChildLinkType

data class Link(
    val childId: String,
    val linkType: ParentChildLinkType,
    val parentId: String
) {
    companion object {
        fun init() = Link(
            childId = "",
            linkType = ParentChildLinkType.BIOLOGICAL,
            parentId = ""
        )
    }
}