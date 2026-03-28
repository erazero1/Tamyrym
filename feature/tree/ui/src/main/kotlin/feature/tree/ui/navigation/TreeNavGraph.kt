package feature.tree.ui.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import core.ui.navigation.Route
import feature.tree.ui.person_detail.PersonDetailScreen
import feature.tree.ui.tree_canvas.TreeCanvasScreen
import feature.tree.ui.tree_list.TreeListScreen

fun NavHostController.navigateToTree(treeId: String) {
    this.navigate(Screen.TreeCanvas(treeId))
}

fun NavHostController.navigateToPerson(personId: String) {
    this.navigate(Screen.PersonDetail(personId))
}

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

        composable<Screen.TreeCanvas> { navBackStackEntry ->
            val route = navBackStackEntry.toRoute<Screen.TreeCanvas>()
            TreeCanvasScreen(
                modifier = modifier,
                treeId = route.treeId,
                onPersonClick = { personId ->
                    navController.navigate(Screen.PersonDetail(personId))
                }
            )
        }

        composable<Screen.PersonDetail> { navBackStackEntry ->
            val route = navBackStackEntry.toRoute<Screen.PersonDetail>()
            PersonDetailScreen(
                modifier = modifier,
                personId = route.personId,
                onEdit = { personId ->
                    TODO()
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}