package feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.domain.model.Tree
import core.presentation.R
import core.ui.theme.AppTheme

@Composable
internal fun RecentTreesSection(
    trees: List<Tree>,
    onClick: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = "Недавно редактировали",
            style = AppTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurface
        )

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(trees) { tree ->
                Column(
                    modifier = Modifier
                        .width(140.dp)
                        .background(AppTheme.colors.surfaceBright, AppTheme.shapes.small)
                        .clickable { onClick(tree.id) }
                        .padding(12.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.family_tree_24px),
                        contentDescription = null,
                        tint = AppTheme.colors.primary,
                    )
                    Text(
                        text = tree.name,
                        style = AppTheme.typography.titleSmall,
                        maxLines = 1
                    )
                    Text(
                        text = tree.description,
                        style = AppTheme.typography.labelSmall,
                        color = AppTheme.colors.outlineCustom,
                        maxLines = 1
                    )
                }
            }
        }
    }
}