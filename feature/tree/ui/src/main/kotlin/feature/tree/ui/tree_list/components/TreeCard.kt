package feature.tree.ui.tree_list.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import core.domain.model.Tree
import core.presentation.R
import core.ui.theme.AppTheme

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun TreeCard(
    modifier: Modifier = Modifier,
    tree: Tree,
    onClick: () -> Unit,
    onEditClick: () -> Unit,
    onAnalyzeClick: (String) -> Unit,
) {
    var isExpanded by remember { mutableStateOf(false) }

    Row(
        modifier = modifier
            .fillMaxWidth()
            // Важно: clip делаем ДО clickable, чтобы ripple-эффект (волна при нажатии) не выходил за края
            .clip(AppTheme.shapes.medium)
            .background(color = AppTheme.colors.primaryContainer)
            .combinedClickable(
                onClick = onClick,
                onLongClick = { isExpanded = true }
            )
            .padding(16.dp), // Увеличили отступ для "воздуха"
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Отступ между иконкой, текстом и меню
    ) {
        // Визуальный якорь - иконка
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(
                    color = AppTheme.colors.onPrimaryContainer.copy(alpha = 0.1f),
                    shape = CircleShape
                ),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.family_tree_24px),
                contentDescription = null,
                tint = AppTheme.colors.onPrimaryContainer
            )
        }

        // Блок с текстами
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = tree.name,
                style = AppTheme.typography.titleMedium,
                color = AppTheme.colors.onPrimaryContainer,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis // Троеточие при длинном имени
            )

            if (tree.description.isNotBlank()) {
                Text(
                    text = tree.description,
                    style = AppTheme.typography.bodyMedium,
                    color = AppTheme.colors.outlineCustom,
                    maxLines = 2, // Ограничиваем описание двумя строками
                    overflow = TextOverflow.Ellipsis
                )
            }
        }

        // Блок меню
        Box {
            IconButton(
                onClick = { isExpanded = true },
                modifier = Modifier.size(32.dp) // Чуть уменьшили область кнопки, чтобы не забирать место
            ) {
                Icon(
                    painter = painterResource(R.drawable.more_vert_24px),
                    contentDescription = stringResource(R.string.more),
                    tint = AppTheme.colors.onPrimaryContainer.copy(alpha = 0.7f), // Слегка приглушили цвет
                )
            }

            DropdownMenu(
                expanded = isExpanded,
                onDismissRequest = { isExpanded = false },
                containerColor = AppTheme.colors.surfaceBright,
                border = BorderStroke(
                    width = 1.dp,
                    color = AppTheme.colors.outline.copy(alpha = 0.5f)
                )
            ) {
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.edit),
                            style = AppTheme.typography.titleMedium
                        )
                    },
                    onClick = {
                        isExpanded = false
                        onEditClick()
                    },
                    leadingIcon = {
                        Icon(
                            painterResource(R.drawable.edit_24px),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
                DropdownMenuItem(
                    text = {
                        Text(
                            stringResource(R.string.ai_analysis),
                            style = AppTheme.typography.titleMedium
                        )
                    },
                    onClick = {
                        isExpanded = false
                        onAnalyzeClick(tree.id)
                    },
                    leadingIcon = {
                        Icon(
                            painterResource(R.drawable.ic_star_shine),
                            contentDescription = null,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                )
            }
        }
    }
}