package feature.home.ui.model

internal sealed interface HomeAction {
    data class ShowToast(val message: String) : HomeAction
}