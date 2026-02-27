package feature.tree.domain.model

import core.domain.model.RelationType

data class AddRelativeRequest(
    val person: PersonRequest,
    val relation: RelationType,
    val relativeId: String
) {
    companion object {
        fun init() = AddRelativeRequest(
            person = PersonRequest.init(),
            relation = RelationType.BROTHER,
            relativeId = ""
        )
    }
}