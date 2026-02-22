package feature.auth.ui.login.components

import android.util.Patterns
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import core.presentation.R
import core.ui.theme.AppTheme

@Composable
internal fun LoginForm(
    modifier: Modifier = Modifier,
    isLoading: Boolean,
    onSendCredentials: (email: String, password: String) -> Unit,
) {
    var email by remember { mutableStateOf("") }
    var emailError by remember { mutableStateOf(false) }
    var password by remember { mutableStateOf("") }

    val isFormValid = remember(email, password, emailError) {
        email.isNotBlank() && password.isNotBlank() && !emailError
    }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                emailError = !Patterns.EMAIL_ADDRESS.matcher(email).matches() && email.isNotEmpty()
            },
            label = { Text(stringResource(R.string.email)) },
            isError = emailError,
            supportingText = {
                if (emailError) {
                    Text(
                        text = stringResource(R.string.enter_valid_email)
                    )
                }
            },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = AppTheme.typography.paragraph,
            enabled = !isLoading
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            visualTransformation = PasswordVisualTransformation(),
            label = { Text(stringResource(R.string.password)) },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            textStyle = AppTheme.typography.paragraph,
            enabled = !isLoading
        )

        Button(
            onClick = { onSendCredentials(email, password) },
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            enabled = isFormValid && !isLoading,
            shape = AppTheme.shapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = AppTheme.colors.primary,
                contentColor = AppTheme.colors.onPrimary,
            )
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(28.dp),
                    color = AppTheme.colors.onPrimary
                )
            } else {
                Text(
                    text = stringResource(R.string.sign_in),
                    style = AppTheme.typography.listItem
                )
            }
        }
    }
}
