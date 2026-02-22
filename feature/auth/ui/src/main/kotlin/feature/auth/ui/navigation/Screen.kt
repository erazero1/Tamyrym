package feature.auth.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface Screen {
    @Serializable
    data object AuthOptions: Screen
    @Serializable
    data object Login: Screen

    @Serializable
    data object Register: Screen
}