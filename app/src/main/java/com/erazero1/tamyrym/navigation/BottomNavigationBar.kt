package com.erazero1.tamyrym.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import core.presentation.R
import core.ui.navigation.Route
import core.ui.theme.AppTheme

@Composable
fun AppBottomBar(
    modifier: Modifier = Modifier,
    navController: NavHostController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    NavigationBar(
        modifier = modifier.graphicsLayer {
            clip = true
            shadowElevation = 100f
        },
        containerColor = AppTheme.colors.surface,
        contentColor = AppTheme.colors.onSurfaceVariant,
        tonalElevation = 0.dp,
    ) {
        bottomNavItems.forEach { item ->
            val isSelected =
                currentDestination?.hierarchy?.any { destination ->
                    destination.hasRoute(item.route::class)
                } == true

            NavigationBarItem(
                selected = isSelected,
                label = {
                    Text(
                        text = stringResource(item.label),
                        style = AppTheme.typography.labelMedium,
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.label)
                    )
                },
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = AppTheme.colors.surface,
                    unselectedIconColor = AppTheme.colors.onSurfaceVariant,
                    indicatorColor = AppTheme.colors.primary,
                )
            )
        }
    }
}

sealed class BottomNavItem<T : Any>(
    val label: Int,
    val icon: Int,
    val route: T,
) {
    object Home : BottomNavItem<Route.Home>(
        label = R.string.home,
        icon = R.drawable.home_24px,
        route = Route.Home,
    )

    object Profile : BottomNavItem<Route.Profile>(
        label = R.string.profile,
        icon = R.drawable.person_24px,
        route = Route.Profile,
    )

    object Tree : BottomNavItem<Route.Tree>(
        label = R.string.tree,
        icon = R.drawable.family_tree_24px,
        route = Route.Tree,
    )
}

val bottomNavItems = listOf(
    BottomNavItem.Home,
    BottomNavItem.Tree,
    BottomNavItem.Profile,
)