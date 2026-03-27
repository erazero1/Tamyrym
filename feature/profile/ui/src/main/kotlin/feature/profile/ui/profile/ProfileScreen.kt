package feature.profile.ui.profile

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import core.presentation.R
import core.ui.theme.AppTheme
import core.ui.uikit.components.ErrorCard
import core.ui.uikit.components.LoadingCard
import core.ui.uikit.effects.SingleEventEffect
import core.ui.utils.showLongToast
import feature.auth.domain.model.User
import feature.profile.ui.profile.components.AppBar
import feature.profile.ui.profile.components.Option
import feature.profile.ui.profile.components.UserCard
import feature.profile.ui.profile.model.ProfileAction
import feature.profile.ui.profile.model.ProfileEvent
import feature.profile.ui.profile.model.ProfileState
import org.koin.androidx.compose.koinViewModel

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
internal fun ProfileScreen(
    modifier: Modifier = Modifier,
    onLogoutClick: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: ProfileViewModel = koinViewModel()
    val state = viewModel.state.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.onEvent(ProfileEvent.LoadProfile)
    }

    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            ProfileAction.OnLogoutClick -> {
                showLongToast(context, context.getString(R.string.successful_logout))
                onLogoutClick()
            }

            is ProfileAction.ShowToast -> {
                showLongToast(context, context.getString(R.string.unknown_error))
            }
        }
    }

    ProfileLayout(
        modifier = modifier,
        state = state.value,
        onEvent = viewModel::onEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileLayout(
    modifier: Modifier = Modifier,
    state: ProfileState,
    onEvent: (ProfileEvent) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            AppBar()
        },
        containerColor = AppTheme.colors.surfaceBright,
    ) { paddingValues ->
        when (state) {
            is ProfileState.Success -> ProfileContent(
                modifier = Modifier
                    .padding(paddingValues)
                    .fillMaxWidth()
                    .padding(8.dp),
                user = state.user,
                onEvent = onEvent
            )

            is ProfileState.Error -> ErrorCard(
                modifier = Modifier.fillMaxSize(),
                message = state.message ?: stringResource(id = R.string.unknown_error),
            )

            is ProfileState.Loading -> LoadingCard(modifier = Modifier.fillMaxSize())
            is ProfileState.Initial -> Unit
        }
    }

}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    user: User,
    onEvent: (ProfileEvent) -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        UserCard(
            modifier = Modifier.fillMaxWidth(),
            user = user,
        )

        Option(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.edit_profile),
            iconId = R.drawable.person_edit_24px,
            onClick = {
                TODO()
            })
        Option(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.change_password),
            iconId = R.drawable.password_24px,
            onClick = {
                TODO()
            })
        Option(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.logout),
            iconId = R.drawable.logout_24px,
            onClick = {
                onEvent(ProfileEvent.OnLogoutClick)
            })

        Spacer(modifier = Modifier)

        Option(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.notifications),
            iconId = R.drawable.notifications_24px,
            onClick = {
                TODO()
            })
        Option(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.language),
            iconId = R.drawable.language_24px,
            onClick = {
                TODO()
            })
        Option(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.dark_mode),
            iconId = R.drawable.dark_mode_24px,
            onClick = {
                TODO()
            })
    }
}