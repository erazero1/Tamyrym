package feature.tree.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import core.ui.navigation.Route
import feature.tree.ui.tree_list.TreeListScreen

fun NavGraphBuilder.treeGraph(modifier: Modifier = Modifier, navController: NavHostController) {
    navigation<Route.Tree>(startDestination = Screen.TreeList) {
        composable<Screen.TreeList> {
            TreeListScreen(
                modifier = modifier,
                onNavigateToTree = { treeId ->
                    navController.navigate(Screen.TreeCanvas(treeId))
                }
            )
        }

        composable<Screen.TreeCanvas> {
            TODO()
        }
    }
}