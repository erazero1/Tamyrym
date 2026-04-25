package feature.tree.ui.tree_canvas.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.domain.model.Gender
import core.ui.theme.AppTheme
import feature.tree.domain.model.PersonInfo
import feature.tree.ui.tree_canvas.model.LayoutConfig

@Composable
internal fun PersonCard(
    person: PersonInfo,
    config: LayoutConfig,
    scale: Float,
    modifier: Modifier = Modifier,
    onCardClick: (String) -> Unit,
    onCardLongClick: (String) -> Unit,
) {
    val density = LocalDensity.current
    val accentColor = when (person.gender) {
        Gender.MALE -> AppTheme.colors.male
        Gender.FEMALE -> AppTheme.colors.female
        Gender.OTHER -> AppTheme.colors.outline
    }

    Box(
        modifier = modifier
            .size(
                width = with(density) { config.nodeWidth.toDp() },
                height = with(density) { config.nodeHeight.toDp() }
            )
            .clip(AppTheme.shapes.medium)
            .background(AppTheme.colors.surface)
            .border(
                width = 1.5.dp,
                color = accentColor.copy(alpha = 0.6f),
                shape = AppTheme.shapes.medium
            )
            .combinedClickable(
                onLongClick = { onCardLongClick(person.id) },
                onClick = { onCardClick(person.id) })
    ) {
        // Accent stripe on the left
        Box(
            modifier = Modifier
                .fillMaxHeight()
                .width(4.dp)
                .background(accentColor)
        )

        // LOD: Hide text content if scale is too small
        if (scale >= 0.4f) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(start = 10.dp, end = 6.dp, top = 8.dp, bottom = 6.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = person.firstName,
                    color = AppTheme.colors.onSurface,
                    style = AppTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = person.lastName,
                    color = AppTheme.colors.onSurface,
                    style = AppTheme.typography.bodySmall,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(Modifier.height(4.dp))
                Text(
                    text = "Gen ${person.generation}",
                    color = AppTheme.colors.onSurfaceVariant,
                    style = AppTheme.typography.labelSmall,
                )
            }
        }
    }
}
