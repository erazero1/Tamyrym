package feature.tree.ui.tree_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme
import feature.tree.domain.model.ai.TreeAnalysisResult

@Composable
internal fun AnalysisResultDialog(
    result: TreeAnalysisResult,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_star_shine),
                    contentDescription = null,
                    tint = AppTheme.colors.primary
                )
                Text(
                    text = stringResource(R.string.ai_analysis),
                    style = AppTheme.typography.titleLarge
                )
            }
        },
        containerColor = AppTheme.colors.surfaceDim,
        shape = AppTheme.shapes.large,
        text = {
            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(result.insights) { insight ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = AppTheme.colors.surfaceBright),
                        shape = AppTheme.shapes.medium
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = insight.title,
                                style = AppTheme.typography.titleMedium,
                                color = AppTheme.colors.primary,
                                fontWeight = FontWeight.SemiBold
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = insight.description,
                                style = AppTheme.typography.bodyMedium,
                                color = AppTheme.colors.onSurface
                            )
                        }
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.ok))
            }
        }
    )
}