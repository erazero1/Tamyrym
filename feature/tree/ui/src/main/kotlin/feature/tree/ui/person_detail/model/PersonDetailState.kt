package feature.tree.ui.person_detail.model

import feature.tree.domain.model.Person

sealed class PersonDetailState {
    data object Initial : PersonDetailState()
    data object Loading : PersonDetailState()
    data class Error(val message: String? = null) : PersonDetailState()
    data class Success(val person: Person) : PersonDetailState()
}