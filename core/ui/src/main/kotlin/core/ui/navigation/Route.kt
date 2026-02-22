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
}