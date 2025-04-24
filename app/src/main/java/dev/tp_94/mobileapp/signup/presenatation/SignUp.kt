package dev.tp_94.mobileapp.signup.presenatation

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.login.presentation.components.PasswordTextEditor
import dev.tp_94.mobileapp.login.presentation.components.PhoneTextEditor
import dev.tp_94.mobileapp.signup.presenatation.components.EmailEditor
import dev.tp_94.mobileapp.signup.presenatation.components.NameEditor

@Composable
fun SignUpStatefulScreen(viewModel: SignUpViewModel = hiltViewModel(), onSuccess: () -> Unit) {
    val state by viewModel.state.collectAsState()
    SignUpStatelessScreen(
        state = state,
        onPhoneNumberChange = { viewModel.updatePhoneNumber(it) },
        onEmailChange = { viewModel.updateEmail(it) },
        onPasswordChange = { viewModel.updatePassword(it) },
        onNameChange = { viewModel.updateName(it) },
        onSignUp = { viewModel.signUp(onSuccess) }
    )
}

@Composable
fun SignUpStatelessScreen(
    state: SignUpState,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onSignUp: () -> Unit
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
            text = "Регистрация",
            style = TextStyles.header(colorResource(R.color.dark_text), fontSize = 32.sp),
            modifier = Modifier
                .padding(0.dp)
        )
        if (!(state.error == null || state.error == "")) {
            Text(
                state.error,
                style = TextStyles.regular(colorResource(R.color.dark_accent))
            )
        }
        Spacer(Modifier.height(26.dp))
        PhoneTextEditor(
            onChange = onPhoneNumberChange,
            text = state.phoneNumber,
        )
        Spacer(Modifier.height(18.dp))
        PasswordTextEditor(
            onChange = onPasswordChange,
            text = state.password,
        )
        NameEditor(
            onChange = onNameChange,
            text = state.name
        )
        EmailEditor(
            onChange = onEmailChange,
            text = state.email,
        )
        Spacer(Modifier.height(18.dp))
        DiscardButton(
            onClick = onSignUp,
            modifier = Modifier
                .width(218.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                if (!state.isLoading) "Зарегистрироваться" else "Регистрируем..."
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignUpStatelessScreen() {
    SignUpStatelessScreen(
        state = SignUpState(),
        onPhoneNumberChange = {  },
        onEmailChange = {  },
        onPasswordChange = {  },
        onNameChange = {  },
        onSignUp = {  }
    )
}