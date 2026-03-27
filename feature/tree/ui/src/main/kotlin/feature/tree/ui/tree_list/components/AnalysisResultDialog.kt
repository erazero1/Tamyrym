package feature.tree.ui.tree_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
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
        title = { Text(text = stringResource(R.string.ai_analysis_result)) },
        containerColor = AppTheme.colors.surfaceDim,
        titleContentColor = AppTheme.colors.onSurface,
        textContentColor = AppTheme.colors.onSurface,
        shape = AppTheme.shapes.small,
        text = {
            // Используем LazyColumn, если инсайтов может быть много
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(result.insights) { insight ->
                    Column {
                        Text(
                            text = insight.title,
                            style = AppTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = insight.description,
                            style = AppTheme.typography.bodyMedium
                        )
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