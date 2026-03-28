package feature.profile.ui.profile.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme
import feature.auth.domain.model.User
import feature.profile.ui.profile.model.ProfileEvent

@Composable
internal fun EditProfileDialog(
    modifier: Modifier = Modifier,
    currentUser: User,
    isSaving: Boolean,
    onConfirm: (ProfileEvent.SubmitProfileEdit) -> Unit,
    onDismiss: () -> Unit,
) {
    // Инициализируем стейт текущими значениями пользователя
    var firstName by remember { mutableStateOf(currentUser.firstName) }
    var lastName by remember { mutableStateOf(currentUser.lastName) }
    var birthYear by remember { mutableStateOf(currentUser.birthYear.toString()) }
    var pictureUrl by remember { mutableStateOf(currentUser.photoUrl ?: "") }
    // Для Gender предполагаем, что у вас есть enum. Оставим пока дефолтным из user,
    // но в реальности здесь нужен бы был DropdownMenu для выбора пола.
    val gender = currentUser.gender

    AlertDialog(
        onDismissRequest = { if (!isSaving) onDismiss() },
        modifier = modifier,
        title = { Text(text = stringResource(R.string.edit_profile)) },
        containerColor = AppTheme.colors.surfaceDim,
        titleContentColor = AppTheme.colors.onSurface,
        textContentColor = AppTheme.colors.onSurface,
        shape = AppTheme.shapes.small,
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = firstName,
                    onValueChange = { firstName = it },
                    label = { Text(stringResource(R.string.first_name)) },
                    enabled = !isSaving
                )
                OutlinedTextField(
                    value = lastName,
                    onValueChange = { lastName = it },
                    label = { Text(stringResource(R.string.last_name)) },
                    enabled = !isSaving
                )
                OutlinedTextField(
                    value = birthYear,
                    onValueChange = { birthYear = it },
                    label = { Text(stringResource(R.string.birth_date)) },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    enabled = !isSaving
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    val year = birthYear.toIntOrNull() ?: currentUser.birthYear
                    onConfirm(
                        ProfileEvent.SubmitProfileEdit(
                            firstName = firstName,
                            lastName = lastName,
                            birthYear = year,
                            gender = gender,
                            pictureUrl = pictureUrl
                        )
                    )
                },
                enabled = !isSaving && firstName.isNotBlank() && lastName.isNotBlank()
            ) {
                if (isSaving) {
                    Text(stringResource(R.string.saving))
                } else {
                    Text(stringResource(R.string.save))
                }
            }
        },
        dismissButton = {
            TextButton(
                onClick = onDismiss,
                enabled = !isSaving
            ) {
                Text(stringResource(R.string.cancel))
            }
        }
    )
}