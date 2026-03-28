package feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme
import feature.home.ui.model.UpcomingBirthday

@Composable
internal fun BirthdaysSection(
    birthdays: List<UpcomingBirthday>,
    onClick: (String) -> Unit,
) {
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text(
            text = stringResource(R.string.upcoming_events),
            style = AppTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurface
        )

        birthdays.forEach { birthday ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(AppTheme.colors.surfaceDim, AppTheme.shapes.small)
                    .clickable { onClick(birthday.personId) }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(AppTheme.colors.primaryContainer, AppTheme.shapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "🥳", // Можно заменить на иконку торта
                        style = AppTheme.typography.titleMedium
                    )
                }
                Spacer(modifier = Modifier.width(12.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = birthday.personName,
                        style = AppTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = "${birthday.dateText} • Исполняется ${birthday.ageTurns}",
                        style = AppTheme.typography.bodyMedium,
                        color = AppTheme.colors.outlineCustom
                    )
                }
            }
        }
    }
}