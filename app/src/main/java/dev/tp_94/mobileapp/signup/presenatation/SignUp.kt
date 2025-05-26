package dev.tp_94.mobileapp.signup.presenatation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
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
import dev.tp_94.mobileapp.core.themes.AddressEditor
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.PasswordTextEditor
import dev.tp_94.mobileapp.core.themes.PhoneEditor
import dev.tp_94.mobileapp.core.themes.EmailEditor
import dev.tp_94.mobileapp.core.themes.NameEditor
import dev.tp_94.mobileapp.core.themes.TextButton
import dev.tp_94.mobileapp.custom_order_settings.presentation.components.LabeledCheckbox

@Composable
fun SignUpStatefulScreen(
    viewModel: SignUpViewModel = hiltViewModel(),
    onSuccessCustomer: () -> Unit,
    onSuccessConfectioner: () -> Unit,
    onLogin: () -> Unit,
    onSkip: () -> Unit
) {
    val state by viewModel.state.collectAsState()
    SignUpStatelessScreen(
        state = state,
        onPhoneNumberChange = { viewModel.updatePhoneNumber(it) },
        onEmailChange = { viewModel.updateEmail(it) },
        onPasswordChange = { viewModel.updatePassword(it) },
        onNameChange = { viewModel.updateName(it) },
        onAddressChange = { viewModel.updateAddress(it) },
        onConfectioner = { viewModel.updateConfectioner(it) },
        onSignUp = { viewModel.signUp(onSuccessCustomer, onSuccessConfectioner) },
        onLogin = onLogin,
        onSkip = onSkip
    )
}

@Composable
fun SignUpStatelessScreen(
    state: SignUpState,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onConfectioner: (Boolean) -> Unit,
    onSignUp: () -> Unit,
    onLogin: () -> Unit,
    onSkip:  () -> Unit
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
        Spacer(Modifier.height(53.dp))
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
        Spacer(Modifier.height(16.dp))
        PhoneEditor(
            onChange = onPhoneNumberChange,
            text = state.phoneNumber,
        )
        Spacer(Modifier.height(8.dp))
        PasswordTextEditor(
            onChange = onPasswordChange,
            text = state.password,
        )
        Spacer(Modifier.height(8.dp))
        NameEditor(
            onChange = onNameChange,
            text = state.name
        )
        Spacer(Modifier.height(8.dp))
        EmailEditor(
            onChange = onEmailChange,
            text = state.email,
        )
        Spacer(Modifier.height(8.dp))
        if (state.isConfectioner) {
            AddressEditor(
                onChange = onAddressChange,
                text = state.address,
            )
            Spacer(Modifier.height(8.dp))
        }
        LabeledCheckbox(
            checked = state.isConfectioner,
            onCheckedChange = onConfectioner,
            label = "Я хочу быть кондитером здесь",
        )
        Spacer(Modifier.height(8.dp))
        DiscardButton(
            onClick = onSignUp,
            modifier = Modifier
                .width(218.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                if (!state.isLoading) "Зарегистрироваться" else "Регистрируем...",
                style = TextStyles.button(),
            )
        }
        TextButton(
            onClick = onSkip,
            modifier = Modifier
                .width(218.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Пропустить",
                style = TextStyles.regular(colorResource(R.color.light_text)),
            )
        }
        Spacer(Modifier.height(8.dp))
        TextButton(
            onClick = onLogin,
            modifier = Modifier
                .width(218.dp)
                .height(48.dp),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Войти",
                style = TextStyles.secondHeader(colorResource(R.color.dark_accent),
                    fontSize = 16.sp),
            )
        }
    }
}

@Preview
@Composable
fun PreviewSignUpStatelessScreen() {
    SignUpStatelessScreen(
        state = SignUpState(),
        onPhoneNumberChange = { },
        onEmailChange = { },
        onPasswordChange = { },
        onNameChange = { },
        onAddressChange = { },
        onConfectioner = { },
        onSignUp = { },
        onLogin = { },
        onSkip = { }
    )
}

@Preview
@Composable
fun PreviewSignUpConfectionerStatelessScreen() {
    val state = SignUpState(
        isConfectioner = true
    );
    SignUpStatelessScreen(
        state = state,
        onPhoneNumberChange = { },
        onEmailChange = { },
        onPasswordChange = { },
        onNameChange = { },
        onAddressChange = { },
        onConfectioner = { },
        onSignUp = { },
        onLogin = { },
        onSkip = { }
    )
}