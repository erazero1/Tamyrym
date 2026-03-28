package feature.tree.ui.tree_list.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme

@Composable
internal fun TreeCreationDialog(
    modifier: Modifier = Modifier,
    title: String,
    initialName: String = "",
    initialDescription: String = "",
    onConfirm: (name: String, description: String) -> Unit,
    onDismiss: () -> Unit,
) {
    var name by remember { mutableStateOf(initialName) }
    var description by remember { mutableStateOf(initialDescription) }

    AlertDialog(
        onDismissRequest = onDismiss,
        modifier = modifier,
        title = {
            Text(
                text = title,
                style = AppTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        },
        containerColor = AppTheme.colors.surfaceDim,
        shape = AppTheme.shapes.large, // Диалогам обычно идут более круглые углы
        text = {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp) // Задает ровный отступ между полями
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text(stringResource(R.string.tree_name)) },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true // Имя обычно в одну строку
                )
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text(stringResource(R.string.tree_description)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 3, // Даем чуть больше места для описания
                    maxLines = 5
                )
            }
        },
        confirmButton = {
            Button( // Выделяем главную кнопку цветом (Button вместо TextButton)
                onClick = {
                    onConfirm(name, description)
                    onDismiss()
                },
                enabled = name.isNotBlank()
            ) {
                Text(stringResource(R.string.create))
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}