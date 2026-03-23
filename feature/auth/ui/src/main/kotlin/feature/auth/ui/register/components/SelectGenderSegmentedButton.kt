package feature.auth.ui.register.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import core.domain.model.Gender
import core.presentation.R

@Composable
fun SelectGenderSegmentedButton(
    modifier: Modifier = Modifier,
    gender: Gender,
    onGenderChanged: (Gender) -> Unit,
) {
    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        Gender.entries.forEachIndexed { index, entryGender ->
            if (entryGender != Gender.OTHER) {
                SegmentedButton(
                    selected = gender == entryGender,
                    onClick = { onGenderChanged(entryGender) },
                    shape = SegmentedButtonDefaults.itemShape(
                        index = index,
                        count = Gender.entries.size - 1
                    ),
                    icon = {
                        SegmentedButtonDefaults.Icon(
                            active = gender == entryGender,
                            activeContent = {
                                Icon(
                                    painter = painterResource(
                                        id = when (entryGender) {
                                            Gender.MALE -> R.drawable.ic_male
                                            Gender.FEMALE -> R.drawable.ic_female
                                            Gender.OTHER -> R.drawable.ic_ellipsis_horiz
                                        }
                                    ),
                                    contentDescription = entryGender.name,
                                    modifier = Modifier.size(20.dp)
                                )
                            }
                        )
                    }
                ) {
                    Text(
                        text = stringResource(
                            when (entryGender) {
                                Gender.MALE -> R.string.male
                                Gender.FEMALE -> R.string.female
                                Gender.OTHER -> R.string.prefer_not_to_say
                            }
                        )
                    )
                }
            }
        }
    }
}