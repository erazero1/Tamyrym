package com.erazero1.tamyrym.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erazero1.splash.presentation.SplashScreen
import core.ui.navigation.Route
import feature.auth.ui.navigation.authGraph

@Composable
internal fun AppNavGraph(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Route.Splash,
    ) {
        composable<Route.Splash> {
            SplashScreen {
                navController.navigate(Route.Auth)
            }
        }
        authGraph(navController = navController)
        composable<Route.Home> {
            TODO()
        }
    }
}