package feature.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme

@Composable
internal fun StatsSection(peopleCount: Int, generationsCount: Int) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        StatCard(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.peoples_in_tree),
            value = peopleCount.toString(),
            iconRes = R.drawable.ic_group
        )
        StatCard(
            modifier = Modifier.weight(1f),
            title = stringResource(R.string.generations),
            value = generationsCount.toString(),
            iconRes = R.drawable.family_tree_24px
        )
    }
}

@Composable
private fun StatCard(modifier: Modifier = Modifier, title: String, value: String, iconRes: Int) {
    Column(
        modifier = modifier
            .background(
                color = AppTheme.colors.primaryContainer,
                shape = AppTheme.shapes.medium
            )
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Icon(
            painter = painterResource(id = iconRes),
            contentDescription = null,
            tint = AppTheme.colors.onPrimaryContainer
        )
        Text(
            text = value,
            style = AppTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold,
            color = AppTheme.colors.onPrimaryContainer
        )
        Text(
            text = title,
            style = AppTheme.typography.labelMedium,
            color = AppTheme.colors.onPrimaryContainer.copy(alpha = 0.8f)
        )
    }
}