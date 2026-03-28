package feature.home.ui.model

import core.domain.model.Tree

data class HomeState(
    val userName: String = "",
    val totalPeople: Int = 0,
    val totalGenerations: Int = 0,
    val upcomingBirthdays: List<UpcomingBirthday> = emptyList(),
    val recentTrees: List<Tree> = emptyList(),
    val isLoading: Boolean = false,
)