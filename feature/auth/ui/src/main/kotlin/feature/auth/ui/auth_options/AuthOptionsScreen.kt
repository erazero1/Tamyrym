package feature.auth.ui.auth_options

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withLink
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.presentation.R
import core.ui.theme.AppTheme
import core.ui.uikit.effects.SingleEventEffect
import core.ui.utils.appendComma
import core.ui.utils.appendSpace
import core.ui.utils.showLongToast
import feature.auth.ui.auth_options.model.AuthOptionsAction
import feature.auth.ui.auth_options.model.AuthOptionsEvent
import feature.auth.ui.auth_options.model.AuthOptionsState
import feature.auth.ui.auth_options.model.CredentialHelper
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AuthOptionsScreen(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    onNavigateToHome: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: AuthOptionsViewModel = koinViewModel()
    val state by viewModel.state.collectAsStateWithLifecycle()
    val helper = remember { CredentialHelper(context) }

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            AuthOptionsAction.NavigateToLogin -> onLoginClick()
            AuthOptionsAction.NavigateToRegister -> onRegisterClick()
            AuthOptionsAction.OnGoogleSignInSuccess -> onNavigateToHome()
            AuthOptionsAction.LaunchGoogleSignIn -> {
                try {
                    val token = helper.getGoogleIdToken()
                    if (token != null) {
                        viewModel.onEvent(AuthOptionsEvent.OnGoogleSignInResult(token))
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    showLongToast(context, context.getString(R.string.unknown_error))
                }
            }
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

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
private fun AuthOptionsContent(
    onEvent: (AuthOptionsEvent) -> Unit,
) {
    Icon(
        painter = painterResource(id = R.drawable.ic_logo),
        contentDescription = stringResource(R.string.app_name),
        modifier = Modifier.size(120.dp),
        tint = Color.Unspecified,
    )
    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = stringResource(R.string.app_name),
        style = AppTheme.typography.displayMedium,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.primary
    )

    Spacer(modifier = Modifier.height(48.dp))

    Button(
        onClick = { onEvent(AuthOptionsEvent.OnGoogleSignInClick) },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = AppTheme.shapes.large,
        colors = ButtonDefaults.buttonColors(
            containerColor = AppTheme.colors.primary,
            contentColor = AppTheme.colors.onPrimary
        ),
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_google),
            contentDescription = "Google icon",
            modifier = Modifier.size(24.dp),
            tint = Color.Unspecified,
        )
        Text(
            text = stringResource(R.string.continue_with_google),
            modifier = Modifier.padding(start = 16.dp),
            style = AppTheme.typography.titleMedium,
            color = AppTheme.colors.onPrimary
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
            style = AppTheme.typography.bodyLarge,
            color = AppTheme.colors.onSurfaceVariant
        )
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            thickness = 1.dp,
            color = AppTheme.colors.outline
        )
    }

    Spacer(modifier = Modifier.height(24.dp))

    OutlinedButton(
        onClick = { onEvent(AuthOptionsEvent.OnRegisterClick) },
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = AppTheme.shapes.large,
        border = BorderStroke(width = 1.dp, color = AppTheme.colors.primary)
    ) {
        Icon(
            painter = painterResource(id = R.drawable.ic_mail),
            contentDescription = "Mail icon",
            modifier = Modifier.size(24.dp),
            tint = AppTheme.colors.onSurface,
        )
        Text(
            modifier = Modifier.padding(start = 16.dp),
            text = stringResource(R.string.sign_up_with_email),
            style = AppTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
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
            style = AppTheme.typography.bodyLarge
        )
        TextButton(
            onClick = { onEvent(AuthOptionsEvent.OnLoginClick) },
        ) {
            Text(
                text = stringResource(R.string.sign_in),
                color = AppTheme.colors.primary,
                style = AppTheme.typography.titleMedium
            )
        }
    }

    Spacer(modifier = Modifier.height(32.dp))

    Text(
        text = buildAnnotatedString {
            append(stringResource(R.string.agreement_prefix))
            appendComma()
            appendSpace()
            withLink(
                LinkAnnotation.Url(
                    url = stringResource(R.string.privacy_policy_link),
                    styles = TextLinkStyles(
                        style = SpanStyle(
                            color = AppTheme.colors.link,
                            fontWeight = FontWeight.W600,
                            textDecoration = TextDecoration.Underline,
                        )
                    )
                )
            ) {
                append(stringResource(R.string.agreement_suffix))
            }
        },
        style = AppTheme.typography.labelLarge,
        textAlign = TextAlign.Center,
        color = AppTheme.colors.onSurfaceVariant
    )

    Spacer(modifier = Modifier.height(32.dp))
}