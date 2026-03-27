package feature.auth.ui.register

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme
import core.ui.uikit.components.ErrorCard
import core.ui.uikit.components.LoadingCard
import core.ui.uikit.components.SelectGenderSegmentedButton
import core.ui.uikit.components.StepProgressBar
import core.ui.uikit.components.YearPickerDialog
import core.ui.uikit.effects.SingleEventEffect
import core.ui.utils.showLongToast
import feature.auth.ui.register.components.AppBar
import feature.auth.ui.register.model.RegisterAction
import feature.auth.ui.register.model.RegisterEvent
import feature.auth.ui.register.model.RegisterState
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RegisterScreen(
    modifier: Modifier = Modifier,
    onBack: () -> Unit,
    onRegister: () -> Unit,
) {
    val context = LocalContext.current
    val viewModel: RegisterViewModel = koinViewModel()
    val state = viewModel.state.collectAsState()


    SingleEventEffect(viewModel.action) { action ->
        when (action) {
            is RegisterAction.RegistrationSuccess -> onRegister()
            is RegisterAction.ShowError -> {
                showLongToast(context, action.message ?: context.getString(R.string.unknown_error))
            }

            is RegisterAction.NotAllFieldsFilled -> {
                showLongToast(context, context.getString(R.string.fill_all_fields))
            }
        }
    }

    RegisterScreenLayout(
        modifier = modifier,
        state = state.value,
        onEvent = viewModel::onEvent,
        onBack = onBack,
    )
}

@Composable
private fun RegisterScreenLayout(
    modifier: Modifier = Modifier,
    state: RegisterState,
    onEvent: (RegisterEvent) -> Unit,
    onBack: () -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = AppTheme.colors.surface,
        topBar = {
            AppBar(onBack = onBack)
        }
    ) { paddingValues ->
        when (state) {
            is RegisterState.Success -> Unit
            is RegisterState.Loading -> {
                LoadingCard(
                    modifier = Modifier.fillMaxSize()
                )
            }

            is RegisterState.Error -> {
                ErrorCard(
                    modifier = Modifier.fillMaxSize(),
                    message = state.message ?: stringResource(R.string.unknown_error),
                )
            }

            is RegisterState.Content -> {
                RegisterScreenContent(
                    paddingValues = paddingValues,
                    state = state,
                    onEvent = onEvent,
                )
            }
        }
    }
}

@Composable
private fun RegisterScreenContent(
    modifier: Modifier = Modifier,
    paddingValues: PaddingValues,
    state: RegisterState.Content,
    onEvent: (RegisterEvent) -> Unit,
) {
    var showDatePicker by remember { mutableStateOf(false) }

    if (showDatePicker) {
        YearPickerDialog(
            onDismissRequest = { showDatePicker = false },
            onYearSelected = { year ->
                onEvent(RegisterEvent.OnBirthYearSelected(year))
                showDatePicker = false
            },
            initialYear = state.birthYear,
        )
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .padding(horizontal = 24.dp)
    ) {
        StepProgressBar(
            numberOfSteps = 2,
            currentStep = state.currentStep,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))

        Crossfade(targetState = state.currentStep, label = "RegisterStep") { step ->
            when (step) {
                1 -> Step1Content(
                    state = state,
                    onEvent = onEvent,
                    onBirthYearClick = { showDatePicker = true }
                )

                2 -> Step2Content(state = state, onEvent = onEvent)
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Step1Content(
    state: RegisterState.Content,
    onEvent: (RegisterEvent) -> Unit,
    onBirthYearClick: () -> Unit,
) {
    Column {
        Text(
            text = stringResource(R.string.tell_about_yourself),
            style = AppTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = state.firstName,
            onValueChange = { onEvent(RegisterEvent.OnFirstNameChanged(it)) },
            label = {
                Text(
                    text = stringResource(R.string.first_name),
                    style = AppTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.lastName,
            onValueChange = { onEvent(RegisterEvent.OnLastNameChanged(it)) },
            label = {
                Text(
                    text = stringResource(R.string.last_name),
                    style = AppTheme.typography.bodyMedium
                )
            },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(text = stringResource(R.string.gender), style = AppTheme.typography.bodyMedium)
        SelectGenderSegmentedButton(
            modifier = Modifier.fillMaxWidth(),
            gender = state.gender,
            onGenderChanged = { gender ->
                onEvent(RegisterEvent.OnGenderChanged(gender))
            }
        )
        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = state.birthYear?.toString().orEmpty(),
            onValueChange = {},
            label = {
                Text(
                    text = stringResource(R.string.year_of_birth),
                    style = AppTheme.typography.bodyMedium,
                )
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = onBirthYearClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = stringResource(R.string.select_a_year),
                    )
                }
            }
        )

        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { onEvent(RegisterEvent.OnContinueClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string._continue))
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_forward),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun Step2Content(state: RegisterState.Content, onEvent: (RegisterEvent) -> Unit) {
    Column {
        Text(
            text = stringResource(R.string.create_your_account),
            style = AppTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(24.dp))

        OutlinedTextField(
            value = state.email,
            onValueChange = {
                onEvent(RegisterEvent.OnEmailChanged(it))
            },
            label = { Text(stringResource(R.string.email)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth(),
            isError = state.emailError,
        )
        Spacer(modifier = Modifier.height(16.dp))
        OutlinedTextField(
            value = state.password,
            onValueChange = {
                onEvent(RegisterEvent.OnPasswordChanged(it))
            },
            label = { Text(stringResource(R.string.password)) },
            visualTransformation = if (state.isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            trailingIcon = {
                IconButton(onClick = { onEvent(RegisterEvent.OnPasswordVisibilityToggle) }) {
                    Icon(
                        painter = painterResource(id = if (state.isPasswordVisible) R.drawable.ic_visibility_on else R.drawable.ic_visibility_off),
                        contentDescription = stringResource(R.string.toggle_password_visibility)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            isError = state.passwordError,
        )
        Text(
            text = stringResource(R.string.password_requirements),
            style = AppTheme.typography.bodySmall
        )

        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = { onEvent(RegisterEvent.OnCompleteRegistrationClick) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
        ) {
            Text(text = stringResource(R.string.finish_registration))
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = { onEvent(RegisterEvent.OnBackClick) },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.size(ButtonDefaults.IconSpacing))
            Text(text = stringResource(R.string.back), fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(24.dp))
    }
}