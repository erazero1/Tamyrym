package feature.tree.ui.person_detail.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import core.domain.model.Gender
import core.presentation.R
import core.ui.uikit.components.SelectGenderSegmentedButton
import feature.tree.domain.model.Person
import feature.tree.domain.model.PersonRequest
import feature.tree.ui.tree_canvas.components.CustomOutlinedTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EditPersonBottomSheet(
    modifier: Modifier = Modifier,
    person: Person,
    isLoading: Boolean,
    onSave: (PersonRequest) -> Unit,
    onDismiss: () -> Unit,
) {
    // Оборачиваем форму в ModalBottomSheet
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    ModalBottomSheet(
        onDismissRequest = { if (!isLoading) onDismiss() },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        modifier = modifier
    ) {
        // Инициализируем стейт текущими значениями персоны
        var firstName by remember { mutableStateOf(person.firstName) }
        var lastName by remember { mutableStateOf(person.lastName) }
        var patronymic by remember { mutableStateOf(person.patronymic) }
        var maidenName by remember { mutableStateOf(person.maidenName ?: "") }
        var gender by remember { mutableStateOf(person.gender) }
        var isAlive by remember { mutableStateOf(person.isAlive) }
        var birthDate by remember { mutableStateOf(person.birthDate.toString()) } // Замените на нужный формат
        var birthPlace by remember { mutableStateOf(person.birthPlace) }
        var deathDate by remember { mutableStateOf(if (!person.isAlive) person.deathDate.toString() else "") }
        var deathPlace by remember { mutableStateOf(if (!person.isAlive) person.deathPlace else "") }
        var biography by remember { mutableStateOf(person.biography) }
        var photoUrl by remember { mutableStateOf(person.photoUrl ?: "") }

        val isFormValid = remember(firstName, lastName) {
            firstName.isNotBlank() && lastName.isNotBlank()
        }

        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
                .verticalScroll(scrollState)
                // Отступ для клавиатуры, чтобы она не перекрывала поля
                .imePadding(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = stringResource(R.string.edit), // "Редактировать"
                style = MaterialTheme.typography.headlineMedium
            )

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            CustomOutlinedTextField(
                value = firstName,
                onValueChange = { firstName = it },
                labelId = R.string.first_name,
                isLoading = isLoading
            )

            CustomOutlinedTextField(
                value = lastName,
                onValueChange = { lastName = it },
                labelId = R.string.last_name,
                isLoading = isLoading
            )

            CustomOutlinedTextField(
                value = patronymic,
                onValueChange = { patronymic = it },
                labelId = R.string.patronymic,
                isLoading = isLoading
            )

            Text(
                text = stringResource(id = R.string.gender),
                style = MaterialTheme.typography.bodyMedium
            )

            SelectGenderSegmentedButton(
                modifier = Modifier.fillMaxWidth(),
                gender = gender,
                onGenderChanged = { gender = it }
            )

            AnimatedVisibility(visible = gender == Gender.FEMALE) {
                CustomOutlinedTextField(
                    value = maidenName,
                    onValueChange = { maidenName = it },
                    labelId = R.string.maiden_name,
                    isLoading = isLoading
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            CustomOutlinedTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                labelId = R.string.birth_date,
                isLoading = isLoading
            )

            CustomOutlinedTextField(
                value = birthPlace,
                onValueChange = { birthPlace = it },
                labelId = R.string.birth_place,
                isLoading = isLoading
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.is_alive),
                    modifier = Modifier.weight(1f),
                    style = MaterialTheme.typography.bodyLarge
                )
                Switch(
                    checked = isAlive,
                    onCheckedChange = { isAlive = it },
                    enabled = !isLoading
                )
            }

            AnimatedVisibility(visible = !isAlive) {
                Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                    CustomOutlinedTextField(
                        value = deathDate,
                        onValueChange = { deathDate = it },
                        labelId = R.string.death_date,
                        isLoading = isLoading
                    )
                    CustomOutlinedTextField(
                        value = deathPlace,
                        onValueChange = { deathPlace = it },
                        labelId = R.string.death_place,
                        isLoading = isLoading
                    )
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

            // Внимание: если у вас есть R.string.photo_url, используйте CustomOutlinedTextField
            OutlinedTextField(
                value = photoUrl,
                onValueChange = { photoUrl = it },
                label = { Text("URL Фото") },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                singleLine = true
            )

            OutlinedTextField(
                value = biography,
                onValueChange = { biography = it },
                label = { Text(stringResource(id = R.string.biography)) },
                modifier = Modifier.fillMaxWidth(),
                enabled = !isLoading,
                minLines = 3,
                maxLines = 5
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val request = PersonRequest(
                        firstName = firstName,
                        lastName = lastName,
                        patronymic = patronymic,
                        maidenName = if (gender == Gender.FEMALE) maidenName else "",
                        gender = gender,
                        isAlive = isAlive,
                        birthDate = birthDate,
                        birthPlace = birthPlace,
                        deathDate = if (!isAlive) deathDate else "",
                        deathPlace = if (!isAlive) deathPlace else "",
                        biography = biography,
                        photoUrl = photoUrl
                    )
                    onSave(request)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                enabled = isFormValid && !isLoading
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(28.dp),
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(text = stringResource(R.string.save))
                }
            }

            // Отступ снизу для безопасной зоны на устройствах без кнопок навигации
            Spacer(modifier = Modifier.windowInsetsBottomHeight(WindowInsets.navigationBars))
        }
    }
}