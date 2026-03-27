package core.ui.uikit.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import core.presentation.R
import core.ui.theme.AppTheme

@Composable
fun AvatarCircle(
    modifier: Modifier = Modifier,
    avatarUrl: String,
) {
    Box(
        modifier = modifier
            .border(width = 2.dp, color = AppTheme.colors.primary, shape = CircleShape),
        contentAlignment = Alignment.Center,
    ) {
        if (avatarUrl.isNotEmpty()) {
            SubcomposeAsyncImage(
                model = avatarUrl,
                contentDescription = null,
                modifier = Modifier
                    .padding(4.dp)
                    .fillMaxSize()
                    .clip(CircleShape),
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center,
                loading = {
                    PlaceholderIcon()
                },
                error = {
                    PlaceholderIcon()
                },
            )
        } else {
            PlaceholderIcon()
        }
    }
}

@Composable
private fun PlaceholderIcon() {
    Icon(
        modifier = Modifier
            .padding(4.dp)
            .fillMaxSize()
            .clip(CircleShape),
        painter = painterResource(id = R.drawable.person_24px),
        contentDescription = null,
        tint = AppTheme.colors.onSurfaceVariant
    )
}
