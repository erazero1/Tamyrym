package com.erazero1.tamyrym.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.erazero1.splash.presentation.SplashScreen
import core.presentation.R
import core.ui.navigation.Route
import feature.auth.ui.navigation.authGraph
import feature.profile.ui.navigation.profileGraph
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
        profileGraph(navController = navController)
        composable<Route.Home> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = stringResource(R.string.home))
            }
        }
    }
}