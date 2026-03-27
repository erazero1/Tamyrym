package feature.tree.ui.tree_canvas.model

import feature.tree.domain.model.AddRelationRequest
import feature.tree.domain.model.PersonRequest

internal sealed interface TreeCanvasEvent {
    data class LoadTreeGraph(val treeId: String, val targetPersonId: String? = null) :
        TreeCanvasEvent

    data class SelectPerson(val personId: String?) : TreeCanvasEvent
    data class AddRelative(val request: AddRelationRequest) : TreeCanvasEvent
    data class AddFirstPerson(val request: PersonRequest) : TreeCanvasEvent
}