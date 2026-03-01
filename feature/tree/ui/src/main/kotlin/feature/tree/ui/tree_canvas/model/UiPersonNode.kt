package feature.tree.ui.tree_canvas.model

import feature.tree.domain.model.PersonExt
import feature.tree.domain.model.Union

data class UiPersonNode(
    val person: PersonExt,
    var x: Float = 0f,
    var y: Float = 0f,
    var generationLevel: Int = 0,
)

data class UiUnionNode(
    val union: Union,
    var x: Float = 0f,
    var y: Float = 0f,
)