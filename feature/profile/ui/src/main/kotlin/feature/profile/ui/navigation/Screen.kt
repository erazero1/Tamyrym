package feature.profile.ui.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface Screen {
    @Serializable
    data object Profile: Screen
}