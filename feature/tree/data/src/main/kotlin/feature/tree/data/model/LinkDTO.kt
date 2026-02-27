package feature.tree.data.model

import core.domain.model.ParentChildLinkType
import feature.tree.domain.model.Link
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class LinkDTO(
    @SerialName("child_id")
    val childId: String?,
    @SerialName("link_type")
    val linkType: String?,
    @SerialName("parent_id")
    val parentId: String?,
)

fun LinkDTO.toDomain(): Link {
    return Link(
        childId = this.childId.orEmpty(),
        linkType = ParentChildLinkType.findValue(this.linkType),
        parentId = this.parentId.orEmpty()
    )
}