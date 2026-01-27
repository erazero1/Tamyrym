package com.erazero1.tamyrym.navigation

import kotlinx.serialization.Serializable

@Serializable
internal sealed interface Route {
    @Serializable
    data object Splash: Route
    @Serializable
    data object Auth: Route
    @Serializable
    data object Home: Route
}