package com.erazero1.tamyrym.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erazero1.splash.presentation.SplashScreen

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
        composable<Route.Auth> {
            TODO()
        }
        composable<Route.Home> {
            TODO()
        }
    }
}