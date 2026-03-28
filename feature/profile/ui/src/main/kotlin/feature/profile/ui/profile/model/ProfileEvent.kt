package feature.profile.ui.profile.model

import core.domain.model.Gender

sealed interface ProfileEvent {
    data object LoadProfile: ProfileEvent
    data object OnLogoutClick : ProfileEvent
    data object OpenEditDialog : ProfileEvent
    data object CloseEditDialog : ProfileEvent
    data class SubmitProfileEdit(
        val firstName: String,
        val lastName: String,
        val birthYear: Int,
        val gender: Gender,
        val pictureUrl: String,
    ) : ProfileEvent
}
