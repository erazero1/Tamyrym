package feature.auth.ui.login

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.presentation.R
import core.ui.uikit.effects.SingleEventEffect
import core.ui.theme.AppTheme
import core.ui.utils.showLongToast
import feature.auth.ui.login.components.LoginForm
import feature.auth.ui.login.model.LoginAction
import feature.auth.ui.login.model.LoginEvent
import feature.auth.ui.login.model.LoginState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LoginScreen(
    modifier: Modifier = Modifier,
    onLoginSuccess: () -> Unit,
    onRegisterClick: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: LoginViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            is LoginAction.ShowToast -> {
                showLongToast(
                    context = context,
                    message = action.message ?: context.getString(R.string.unknown_error),
                )
            }

            is LoginAction.SuccessfulLogin -> {
                onLoginSuccess()
            }
        }
    }

    LoginLayout(
        modifier = modifier,
        state = state,
        onRegisterClick = onRegisterClick,
        onEvent = viewModel::onEvent,
    )
}

@Composable
private fun LoginLayout(
    modifier: Modifier = Modifier,
    state: LoginState,
    onRegisterClick: () -> Unit,
    onEvent: (LoginEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppTheme.colors.surface,
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = AppTheme.typography.h1,
                color = AppTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.login_to_your_account),
                style = AppTheme.typography.paragraph,
                color = AppTheme.colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))

            LoginForm(
                modifier = Modifier.fillMaxWidth(),
                isLoading = state is LoginState.Loading,
                onSendCredentials = { email, password ->
                    onEvent(LoginEvent.OnSendCredentials(email, password))
                },
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.dont_have_account),
                    color = AppTheme.colors.onSurfaceVariant,
                    style = AppTheme.typography.hint
                )
                TextButton(
                    onClick = onRegisterClick,
                ) {
                    Text(
                        text = stringResource(R.string.register),
                        color = AppTheme.colors.primary,
                        style = AppTheme.typography.hint
                    )
                }
            }
        }
    }
}
