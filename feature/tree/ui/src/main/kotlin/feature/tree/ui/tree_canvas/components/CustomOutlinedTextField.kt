package feature.tree.ui.tree_canvas.components

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource

@Composable
internal fun CustomOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    @StringRes labelId: Int,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(stringResource(id = labelId)) },
        modifier = modifier.fillMaxWidth(),
        enabled = !isLoading,
        singleLine = true
    )
}
