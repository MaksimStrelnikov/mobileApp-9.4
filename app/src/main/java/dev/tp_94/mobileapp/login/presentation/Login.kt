package dev.tp_94.mobileapp.login.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.PasswordTextEditor
import dev.tp_94.mobileapp.core.themes.PhoneEditor
import dev.tp_94.mobileapp.core.themes.TextButton

@Composable
fun LoginStatefulScreen(viewModel: LoginViewModel = hiltViewModel(), onSignUp: () -> Unit, onSuccessCustomer: () -> Unit, onSuccessConfectioner: () -> Unit, onSkip: () -> Unit) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    LoginStatelessScreen(
        state = state,
        onPhoneNumberChange = { viewModel.updatePhoneNumber(it) },
        onPasswordChange = { viewModel.updatePassword(it) },
        onSignUp = onSignUp,
        onLogin = { viewModel.login(onSuccessCustomer, onSuccessConfectioner) },
        onSkip = onSkip,
    )
}

@Composable
fun LoginStatelessScreen(
    state: LoginState,
    onPhoneNumberChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onSignUp: () -> Unit,
    onLogin: () -> Unit,
    onSkip: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.background)
            )
            .verticalScroll(rememberScrollState())
            .padding(44.dp, 52.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        Image(
            painter = painterResource(R.drawable.login_name),
            contentDescription = null,
            alignment = Alignment.Center
        )
        Spacer(Modifier.height(111.dp))
        Text(
            text = "Вход",
            style = TextStyles.header(colorResource(R.color.dark_text), fontSize = 32.sp),
            modifier = Modifier
                .padding(0.dp)
        )
        if (!(state.error == null || state.error == "")) {
            Text(
                state.error,
                style = TextStyles.regular(colorResource(R.color.dark_accent)),

            )
        }
        Spacer(Modifier.height(26.dp))
        PhoneEditor(
            onChange = onPhoneNumberChange,
            text = state.phoneNumber,
        )
        Spacer(Modifier.height(9.dp))
        PasswordTextEditor(
            onChange = onPasswordChange,
            text = state.password,
        )
        Spacer(Modifier.height(18.dp))
        DiscardButton(
            onClick = onLogin,
            modifier = Modifier
                .width(218.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            enabled = !state.isLoading
        ) {
            Text(
                if (!state.isLoading) "Войти" else "Входим...",
                style = TextStyles.button()
            )
        }
        Spacer(Modifier.height(8.dp))
        ActiveButton(
            onClick = onSignUp,
            modifier = Modifier
                .width(218.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Зарегистрироваться",
                style = TextStyles.button(color = colorResource(R.color.light_background))
            )
        }
        Spacer(Modifier.height(16.dp))
        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .width(218.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Пропустить",
                style = TextStyles.regular(color = colorResource(R.color.light_text))
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoginStatelessScreen() {
    MaterialTheme {
        LoginStatelessScreen(
            state = LoginState(),
            onPhoneNumberChange = {  },
            onPasswordChange = {  },
            onSignUp = {  },
            onLogin = {  },
            onSkip = {  },
        )
    }
}