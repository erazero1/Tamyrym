package feature.home.ui.model

internal sealed interface HomeEvent {
    data object LoadData : HomeEvent
}