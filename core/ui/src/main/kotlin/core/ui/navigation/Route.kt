package core.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface Route {
    @Serializable
    data object Splash: Route
    @Serializable
    data object Auth: Route
    @Serializable
    data object Home: Route
    @Serializable
    data object Profile: Route
    @Serializable
    data object Tree: Route
}