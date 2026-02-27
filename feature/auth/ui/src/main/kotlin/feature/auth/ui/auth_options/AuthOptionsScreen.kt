package feature.auth.ui.auth_options

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.presentation.R
import core.ui.uikit.effects.SingleEventEffect
import core.ui.theme.AppTheme
import feature.auth.ui.auth_options.model.AuthOptionsAction
import feature.auth.ui.auth_options.model.AuthOptionsEvent
import feature.auth.ui.auth_options.model.AuthOptionsState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AuthOptionsScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onGoogleSignInClick: () -> Unit,
) {
    val viewModel: AuthOptionsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            AuthOptionsAction.NavigateToLogin -> onLoginClick()
            AuthOptionsAction.NavigateToRegister -> onRegisterClick()
            AuthOptionsAction.HandleGoogleSignIn -> onGoogleSignInClick()
        }
    }

    AuthOptionsLayout(
        modifier = modifier,
        state = state,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun AuthOptionsLayout(
    modifier: Modifier = Modifier,
    state: AuthOptionsState,
    onEvent: (AuthOptionsEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppTheme.colors.surface,
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
            ) {
                if (state is AuthOptionsState.Loading) {
                    CircularProgressIndicator()
                } else {
                    AuthOptionsContent(onEvent)
                }
            }
        }
    }
}

@Composable
private fun AuthOptionsContent(
    onEvent: (AuthOptionsEvent) -> Unit,
) {
    Image(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(R.string.app_name),
        modifier = Modifier.size(120.dp)
    )

    Spacer(modifier = Modifier.height(48.dp))

    OutlinedButton(
        onClick = { onEvent(AuthOptionsEvent.OnGoogleSignInClick) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = AppTheme.shapes.small,
        border = BorderStroke(width = 1.dp, color = AppTheme.colors.primary)
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google icon",
            modifier = Modifier.size(24.dp)
        )
        Text(
            text = stringResource(R.string.continue_with_google),
            modifier = Modifier.padding(start = 16.dp),
            style = AppTheme.typography.listItem,
            color = AppTheme.colors.onSurface
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = AppTheme.colors.outline
        )
        Text(
            text = stringResource(R.string.or),
            modifier = Modifier.padding(horizontal = 16.dp),
            style = AppTheme.typography.paragraph,
            color = AppTheme.colors.onSurfaceVariant
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = AppTheme.colors.outline
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    Button(
        onClick = { onEvent(AuthOptionsEvent.OnRegisterClick) },
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = AppTheme.shapes.small,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.onPrimary
        )
    ) {
        Text(
            text = stringResource(R.string.sign_up_with_email),
            style = AppTheme.typography.listItem
        )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = stringResource(R.string.already_have_account),
            color = AppTheme.colors.onSurfaceVariant,
            style = AppTheme.typography.paragraph
        )
        TextButton(
            onClick = { onEvent(AuthOptionsEvent.OnLoginClick) },
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                color = AppTheme.colors.primary,
                style = AppTheme.typography.listItem
            )
        }
    }


    Text(
        text = stringResource(R.string.agreement_prefix),
        style = AppTheme.typography.hint,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(32.dp))
}