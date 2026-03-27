package feature.profile.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import core.ui.theme.AppTheme
import core.ui.uikit.components.AvatarCircle
import feature.auth.domain.model.User

@Composable
internal fun UserCard(
    modifier: Modifier = Modifier,
    user: User,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
    ) {
        AvatarCircle(
            modifier = Modifier.size(128.dp),
            avatarUrl = user.photoUrl.orEmpty(),
        )
        Text(
            text = "${user.firstName} ${user.lastName}",
            style = AppTheme.typography.titleMedium,
            color = AppTheme.colors.onSurface,
        )

        Text(
            text = user.email,
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onSurface,
        )
    }
}