package feature.tree.ui.tree_canvas.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
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
import core.domain.model.RelationType
import core.presentation.R
import core.ui.uikit.components.SelectGenderSegmentedButton
import feature.tree.domain.model.PersonRequest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun AddRelativeBottomSheet(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    isFirstPerson: Boolean = false,
    onSave: (person: PersonRequest, relationType: RelationType?) -> Unit,
) {
    var relationType by remember { mutableStateOf(RelationType.BROTHER) }
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var patronymic by remember { mutableStateOf("") }
    var maidenName by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf(Gender.MALE) }
    var isAlive by remember { mutableStateOf(true) }
    var birthDate by remember { mutableStateOf("") }
    var birthPlace by remember { mutableStateOf("") }
    var deathDate by remember { mutableStateOf("") }
    var deathPlace by remember { mutableStateOf("") }
    var biography by remember { mutableStateOf("") }

    var expandedRelation by remember { mutableStateOf(false) }

    val isFormValid = remember(firstName, lastName) {
        firstName.isNotBlank() && lastName.isNotBlank()
    }

    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp)
            .padding(bottom = 24.dp)
            .verticalScroll(scrollState)
            .imePadding(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.add_relative),
            style = MaterialTheme.typography.headlineMedium
        )

        if (!isFirstPerson) {
            RelationTypeDropdown(
                expanded = expandedRelation,
                relationType = relationType,
                onExpandedChange = { expandedRelation = it },
                onRelationSelected = { relationType = it }
            )
            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
        }

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
            labelId = R.string.patronymic, // 
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
                    photoUrl = ""
                )
                onSave(request, if (isFirstPerson) null else relationType)
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
    }
}