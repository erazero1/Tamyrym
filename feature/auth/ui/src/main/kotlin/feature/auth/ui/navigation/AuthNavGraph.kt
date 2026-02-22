package feature.auth.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import core.ui.navigation.Route
import feature.auth.ui.auth_options.AuthOptionsScreen
import feature.auth.ui.login.LoginScreen
import feature.auth.ui.register.RegisterScreen


fun NavGraphBuilder.authGraph(navController: NavHostController) {
    navigation<Route.Auth>(startDestination = Screen.Login) {
        composable<Screen.AuthOptions> {
            AuthOptionsScreen(
                onGoogleClick = {},
                onRegisterClick = {},
                onLoginClick = {},
            )
        }
        composable<Screen.Login> {
            LoginScreen(
                onLoginSuccess = { navController.navigate(Route.Home) },
                onRegisterClick = { navController.navigate(Screen.Register) },
            )
        }
        composable<Screen.Register> {
            RegisterScreen(
                onBack = { navController.popBackStack() },
            )
        }
    }
}