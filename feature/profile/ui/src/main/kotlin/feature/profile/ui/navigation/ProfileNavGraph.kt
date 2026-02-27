package feature.profile.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import core.ui.navigation.Route
import feature.profile.ui.profile.ProfileScreen

fun NavGraphBuilder.profileGraph(navController: NavHostController) {
    navigation<Route.Profile>(startDestination = Screen.Profile) {
        composable<Screen.Profile> {
            ProfileScreen(
                onLogoutClick = {
                    navController.navigate(Route.Auth) {
                        popUpTo<Route.Home> {
                            inclusive = true
                        }
                    }
                },
            )
        }
    }
}
