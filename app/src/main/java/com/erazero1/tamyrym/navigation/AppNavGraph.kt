package com.erazero1.tamyrym.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erazero1.splash.presentation.SplashScreen
import core.ui.navigation.Route
import feature.auth.ui.navigation.authGraph
import feature.home.ui.navigation.homeGraph
import feature.profile.ui.navigation.profileGraph
import feature.tree.ui.navigation.navigateToPerson
import feature.tree.ui.navigation.navigateToTree
import feature.tree.ui.navigation.treeGraph

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
            SplashScreen { isLoggedIn ->
                if (isLoggedIn) {
                    navController.navigate(Route.Home) {
                        popUpTo(Route.Splash) { inclusive = true }
                    }
                } else {
                    navController.navigate(Route.Auth) {
                        popUpTo(Route.Splash) { inclusive = true }
                    }
                }
            }
        }
        authGraph(navController = navController)
        treeGraph(modifier = modifier, navController = navController)
        profileGraph(modifier = modifier, navController = navController)
        homeGraph(
            modifier = modifier,
            navController = navController,
            navigateToTree = { treeId ->
                navController.navigateToTree(treeId)
            },
            navigateToPerson = { personId ->
                navController.navigateToPerson(personId)
            }
        )
    }
}
