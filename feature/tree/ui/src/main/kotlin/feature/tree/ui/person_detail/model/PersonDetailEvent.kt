package feature.tree.ui.person_detail.model

interface PersonDetailEvent {
    data class LoadPerson(val personId: String) : PersonDetailEvent
}