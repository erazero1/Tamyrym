package feature.profile.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme
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
        Icon(
            painter = painterResource(R.drawable.person_24px),
            contentDescription = null,
            modifier = Modifier
                .size(96.dp)
                .clip(CircleShape),
            tint = AppTheme.colors.onSurface,
        )

        Text(
            text = "${user.firstName} ${user.lastName}",
            style = AppTheme.typography.listItem,
            color = AppTheme.colors.onSurface,
        )

        Text(
            text = user.email,
            style = AppTheme.typography.paragraph,
            color = AppTheme.colors.onSurface,
        )
    }
}