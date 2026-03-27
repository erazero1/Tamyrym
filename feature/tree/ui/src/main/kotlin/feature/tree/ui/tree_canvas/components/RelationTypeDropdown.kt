package feature.tree.ui.tree_canvas.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import core.domain.model.RelationType
import core.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun RelationTypeDropdown(
    expanded: Boolean,
    relationType: RelationType,
    onExpandedChange: (Boolean) -> Unit,
    onRelationSelected: (RelationType) -> Unit,
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = onExpandedChange,
    ) {
        val fillMaxWidth = Modifier
            .fillMaxWidth()
        OutlinedTextField(
            modifier = fillMaxWidth.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryEditable),
            readOnly = true,
            value = relationType.name.lowercase().replaceFirstChar { it.uppercase() },
            onValueChange = {},
            label = { Text(stringResource(R.string.relation_type)) },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange(false) },
        ) {
            RelationType.entries.forEach { selectionOption ->
                DropdownMenuItem(
                    text = {
                        Text(selectionOption.name.lowercase().replaceFirstChar { it.uppercase() })
                    },
                    onClick = {
                        onRelationSelected(selectionOption)
                        onExpandedChange(false)
                    },
                    contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                )
            }
        }
    }
}