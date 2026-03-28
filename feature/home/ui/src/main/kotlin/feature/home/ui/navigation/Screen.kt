package feature.home.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface Screen {
    @Serializable
    data object Home : Screen
}