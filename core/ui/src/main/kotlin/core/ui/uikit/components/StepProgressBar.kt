package core.ui.uikit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import core.ui.theme.AppTheme

@Composable
fun StepProgressBar(
    modifier: Modifier = Modifier,
    numberOfSteps: Int,
    currentStep: Int,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        for (step in 1..numberOfSteps) {
            val isActive = step == currentStep
            HorizontalDivider(
                modifier = Modifier
                    .weight(1f)
                    .height(4.dp)
                    .clip(RoundedCornerShape(2.dp)),
                color = if (isActive) AppTheme.colors.primary else AppTheme.colors.surfaceBright
            )
        }
    }
}
