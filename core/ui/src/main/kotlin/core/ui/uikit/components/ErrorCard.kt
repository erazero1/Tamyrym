package core.ui.uikit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme

@Composable
fun ErrorCard(
    modifier: Modifier = Modifier,
    message: String = "",
    onTryAgainClick: (() -> Unit)? = null,
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier.semantics { heading() },
            text = stringResource(id = R.string.unknown_error),
            style = AppTheme.typography.h3,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (message.isNotBlank()) {
            Text(
                text = message,
                style = AppTheme.typography.paragraph,
                color = AppTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.Center,
            )
            Spacer(modifier = Modifier.height(12.dp))
        }
        if (onTryAgainClick != null) {
            Button(
                onClick = { onTryAgainClick.invoke() },
                shape = AppTheme.shapes.medium,
                colors = ButtonDefaults.buttonColors(
                    contentColor = AppTheme.colors.onPrimary,
                    containerColor = AppTheme.colors.primary,
                    disabledContentColor = AppTheme.colors.onSurfaceVariant,
                    disabledContainerColor = AppTheme.colors.surfaceDim,
                ),
            ) {
                Text(
                    modifier = Modifier.padding(vertical = 8.dp),
                    text = stringResource(id = R.string.try_again),
                    style = AppTheme.typography.paragraph,
                )
            }
        }
    }
}