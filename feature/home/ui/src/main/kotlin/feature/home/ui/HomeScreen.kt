package feature.home.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import core.ui.theme.AppTheme
import core.ui.uikit.components.LoadingCard
import core.ui.uikit.effects.SingleEventEffect
import core.ui.utils.showLongToast
import feature.home.ui.components.BirthdaysSection
import feature.home.ui.components.GreetingSection
import feature.home.ui.components.RecentTreesSection
import feature.home.ui.components.StatsSection
import feature.home.ui.model.HomeAction
import feature.home.ui.model.HomeState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    onNavigateToTree: (treeId: String) -> Unit,
    onNavigateToPerson: (personId: String) -> Unit,
) {
    val context = LocalContext.current
    val viewModel: HomeViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            is HomeAction.ShowToast -> {
                showLongToast(context, action.message)
            }
        }
    }

    if (state.isLoading && state.userName.isEmpty()) {
        LoadingCard(modifier = Modifier.fillMaxSize())
    } else {
        HomeContent(
            modifier = modifier,
            state = state,
            onNavigateToTree = onNavigateToTree,
            onNavigateToPerson = onNavigateToPerson
        )
    }
}

@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeState,
    onNavigateToTree: (treeId: String) -> Unit,
    onNavigateToPerson: (personId: String) -> Unit,
) {
    Scaffold(
        modifier = modifier,
        containerColor = AppTheme.colors.surface,
    ) { paddingValues ->
        LazyColumn(
            modifier = modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            contentPadding = PaddingValues(top = 24.dp, bottom = 24.dp)
        ) {
            item {
                GreetingSection(userName = state.userName)
            }

            item {
                StatsSection(
                    peopleCount = state.totalPeople,
                    generationsCount = state.totalGenerations
                )
            }

            if (state.upcomingBirthdays.isNotEmpty()) {
                item {
                    BirthdaysSection(
                        birthdays = state.upcomingBirthdays,
                        onClick = onNavigateToPerson
                    )
                }
            }

            if (state.recentTrees.isNotEmpty()) {
                item {
                    RecentTreesSection(
                        trees = state.recentTrees,
                        onClick = onNavigateToTree
                    )
                }
            }
        }
    }
}