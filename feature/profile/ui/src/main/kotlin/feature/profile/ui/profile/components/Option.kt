package feature.profile.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme

@Composable
internal fun Option(
    modifier: Modifier = Modifier,
    text: String,
    iconId: Int,
    onClick: () -> Unit,
) {
    Card(
        modifier = modifier,
        shape = AppTheme.shapes.small,
        onClick = onClick,
        colors = CardDefaults.cardColors(
            contentColor = AppTheme.colors.onSurface,
            containerColor = AppTheme.colors.surface,
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                painter = painterResource(iconId),
                contentDescription = text,
                modifier = Modifier.size(32.dp)
            )
            Text(
                modifier = Modifier.weight(1f),
                text = text,
                style = AppTheme.typography.titleMedium,
            )
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .rotate(270f),
                painter = painterResource(id = R.drawable.ic_caret_down_24px),
                contentDescription = null,
            )
        }
    }
}