package feature.tree.ui.tree_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme
import feature.tree.domain.model.Tree

@Composable
internal fun TreeCard(
    modifier: Modifier = Modifier,
    tree: Tree,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .background(
                color = AppTheme.colors.primaryContainer,
                shape = AppTheme.shapes.small
            )
            .clickable(
                onClick = onClick,
            )
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = tree.name,
                style = AppTheme.typography.titleMedium,
                color = AppTheme.colors.onPrimaryContainer,
            )
            Text(
                text = tree.description,
                style = AppTheme.typography.labelLarge,
                color = AppTheme.colors.outlineCustom,
            )
        }
        Box(
            modifier = Modifier
        ) {
            IconButton(
                onClick = {
                    isExpanded = true
                },
                shape = AppTheme.shapes.small,
            ) {
                Icon(
                    modifier = Modifier.size(24.dp),
                    painter = painterResource(R.drawable.more_vert_24px),
                    contentDescription = stringResource(R.string.more),
                    tint = AppTheme.colors.onPrimaryContainer,
                )
            }
            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = {
                    isExpanded = false
                },
                modifier = Modifier,
                containerColor = AppTheme.colors.surfaceBright,
                border = BorderStroke(width = 1.dp, color = AppTheme.colors.outline)
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            text = stringResource(R.string.edit),
                            style = AppTheme.typography.titleMedium,
                            color = AppTheme.colors.onSecondaryContainer
                        )
                    },
                    onClick = {
                        onEditClick()
                    },
                    modifier = Modifier,
                    leadingIcon = {
                        Icon(
                            painter = painterResource(R.drawable.edit_24px),
                            contentDescription = stringResource(R.string.edit),
                            tint = AppTheme.colors.onSurface,
                            modifier = Modifier.size(24.dp)
                        )
                    },
                )
            }
        }
    }
}