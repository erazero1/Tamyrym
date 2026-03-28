package feature.home.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import core.presentation.R
import core.ui.theme.AppTheme
import java.util.Calendar

@Composable
internal fun GreetingSection(userName: String) {
    val currentHour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)
    val greeting = stringResource(
        when (currentHour) {
            in 4..11 -> R.string.good_morning
            in 12..16 -> R.string.good_afternoon
            in 17..22 -> R.string.good_evening
            else -> R.string.good_night
        }
    )

    Column {
        Text(
            text = "$greeting,",
            style = AppTheme.typography.titleLarge,
            color = AppTheme.colors.outlineCustom
        )
        Text(
            text = "$userName!",
            style = AppTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onSurface
        )
    }
}