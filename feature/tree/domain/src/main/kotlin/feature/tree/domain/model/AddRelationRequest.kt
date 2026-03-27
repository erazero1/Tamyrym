package feature.tree.domain.model

import core.domain.model.RelationType

data class AddRelationRequest(
    val person: PersonRequest,
    val personIdToFocus: String,
    val relationType: RelationType? = null,
) {
    companion object {
        fun init() = AddRelationRequest(
            person = PersonRequest.init(),
            personIdToFocus = "",
            relationType = RelationType.BROTHER
        )
    }
}