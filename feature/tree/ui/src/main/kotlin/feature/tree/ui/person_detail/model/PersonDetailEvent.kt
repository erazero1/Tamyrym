package feature.tree.ui.person_detail.model

import feature.tree.domain.model.PersonRequest

interface PersonDetailEvent {
    data class LoadPerson(val personId: String) : PersonDetailEvent
    data object OpenEditDialog : PersonDetailEvent
    data object CloseEditDialog : PersonDetailEvent
    data class SubmitPersonEdit(val personId: String, val request: PersonRequest) :
        PersonDetailEvent
}