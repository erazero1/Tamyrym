package feature.tree.ui.person_detail.model

sealed interface PersonDetailAction {
    data class ShowToast(val message: String) : PersonDetailAction
}