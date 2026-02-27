package feature.profile.ui.profile.model

import feature.auth.domain.model.User

internal sealed class ProfileState {
    data object Initial : ProfileState()
    data object Loading : ProfileState()
    data class Success(val user: User) : ProfileState()
    data class Error(val message: String? = null) : ProfileState()
}
