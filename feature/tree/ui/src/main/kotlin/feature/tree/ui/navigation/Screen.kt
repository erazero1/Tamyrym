package feature.tree.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface Screen {
    @Serializable
    data object TreeList : Screen

    @Serializable
    data class TreeCanvas(val treeId: String) : Screen

    @Serializable
    data class PersonDetail(val personId: String) : Screen
}