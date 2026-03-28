package feature.home.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import core.ui.navigation.Route
import feature.home.ui.HomeScreen

fun NavGraphBuilder.homeGraph(
    modifier: Modifier = Modifier,
    navigateToTree: (String) -> Unit,
    navigateToPerson: (String) -> Unit,
    navController: NavHostController,
) {
    navigation<Route.Home>(startDestination = Screen.Home) {
        composable<Screen.Home> {
            HomeScreen(
                modifier = modifier,
                onNavigateToTree = { treeId ->
                    navigateToTree(treeId)
                },
                onNavigateToPerson = { personId ->
                    navigateToPerson(personId)
                }
            )
        }
    }
}