package dev.tp_94.mobileapp.profile_editor.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.AddressEditor
import dev.tp_94.mobileapp.core.themes.DescriptionEditor
import dev.tp_94.mobileapp.core.themes.EmailEditor
import dev.tp_94.mobileapp.core.themes.NameEditor
import dev.tp_94.mobileapp.core.themes.PhoneEditor
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar

@Composable
fun ProfileEditorStatefulScreen(
    viewModel: ProfileEditorViewModel = hiltViewModel(),
    onError: () -> Unit,
    onSave: () -> Unit,
    topBar: @Composable () -> Unit
) {
    //TODO: rewrite onError logic
    val state = viewModel.state.collectAsStateWithLifecycle()
    if (state.value == null) {
        onError()
    }
    when (state.value!!) {
        is Confectioner -> ProfileEditorStatefulConfectionerScreen(onSave = onSave, topBar = topBar)

        is Customer -> ProfileEditorStatefulCustomerScreen(onSave = onSave, topBar = topBar)
    }
}

@Composable
fun ProfileEditorStatelessCustomerScreen(
    state: ProfileEditorCustomerState,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onSave: () -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(topBar = topBar) { internalPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(R.color.background)
                )
                .verticalScroll(rememberScrollState())
                .padding(internalPadding)
                .padding(44.dp, 0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(16.dp))
            NameEditor(
                onChange = { onNameChange(it) },
                text = state.name,
                backgroundColor = colorResource(R.color.dark_background)
            )
            Text(
                text = "Контакты",
                style = TextStyles.secondHeader(colorResource(R.color.dark_text)),
                modifier = Modifier
                    .padding(0.dp, 23.dp)
            )
            PhoneEditor(
                onChange = { onPhoneNumberChange(it) },
                text = state.phoneNumber,
                backgroundColor = colorResource(R.color.dark_background)
            )
            Spacer(Modifier.height(8.dp))
            EmailEditor(
                onChange = { onEmailChange(it) },
                text = state.email,
                backgroundColor = colorResource(R.color.dark_background)
            )
            if (!(state.error == null || state.error == "")) {
                Text(
                    state.error,
                    style = TextStyles.regular(colorResource(R.color.dark_accent))
                )
            } else {
                Spacer(Modifier.height(18.dp))
            }
            ActiveButton(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (!state.isLoading) "Cохранить" else "Сохраняем...",
                    style = TextStyles.button(colorResource(R.color.light_background))
                )
            }
        }
    }
}

@Composable
fun ProfileEditorStatefulCustomerScreen(
    viewModel: ProfileEditorCustomerViewModel = hiltViewModel(),
    onSave: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    ProfileEditorStatelessCustomerScreen(
        state.value,
        onNameChange = { viewModel.updateName(it) },
        onPhoneNumberChange = { viewModel.updatePhoneNumber(it) },
        onEmailChange = { viewModel.updateEmail(it) },
        onSave = {
            viewModel.save(onSave)
        },
        topBar = topBar
    )
}

@Preview
@Composable
fun PreviewProfileEditorStatelessCustomerScreen() {
    MaterialTheme {
        ProfileEditorStatelessCustomerScreen(
            state = ProfileEditorCustomerState(),
            onNameChange = {},
            onPhoneNumberChange = {},
            onEmailChange = {},
            onSave = {},
            topBar = { TopNameBar("Личные данные") { } }
        )
    }
}

@Composable
fun ProfileEditorStatelessConfectionerScreen(
    state: ProfileEditorConfectionerState,
    onNameChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onAddressChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSave: () -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(topBar = topBar) { internalPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(R.color.background)
                )
                .verticalScroll(rememberScrollState())
                .padding(internalPadding)
                .padding(44.dp, 0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            NameEditor(
                onChange = { onNameChange(it) },
                text = state.name,
                defaultText = "Название (Имя)",
                backgroundColor = colorResource(R.color.dark_background)
            )
            Spacer(Modifier.height(8.dp))
            AddressEditor(
                onChange = { onAddressChange(it) },
                text = state.address,
                backgroundColor = colorResource(R.color.dark_background)
            )
            Text(
                text = "Контакты",
                style = TextStyles.secondHeader(colorResource(R.color.dark_text)),
                modifier = Modifier
                    .padding(0.dp, 23.dp)
            )
            PhoneEditor(
                onChange = { onPhoneNumberChange(it) },
                text = state.phoneNumber,
                backgroundColor = colorResource(R.color.dark_background)
            )
            Spacer(Modifier.height(8.dp))
            EmailEditor(
                onChange = { onEmailChange(it) },
                text = state.email,
                backgroundColor = colorResource(R.color.dark_background)
            )
            Spacer(Modifier.height(8.dp))
            DescriptionEditor(
                onChange = { onDescriptionChange(it) },
                text = state.description,
                modifier = Modifier.height(108.dp),
                backgroundColor = colorResource(R.color.dark_background)
            )
            if (!(state.error == null || state.error == "")) {
                Text(
                    state.error,
                    style = TextStyles.regular(colorResource(R.color.dark_accent))
                )
            } else {
                Spacer(Modifier.height(18.dp))
            }
            ActiveButton(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (!state.isLoading) "Cохранить" else "Сохраняем...",
                    style = TextStyles.button(colorResource(R.color.light_background))
                )
            }
        }
    }
}

@Composable
fun ProfileEditorStatefulConfectionerScreen(
    viewModel: ProfileEditorConfectionerViewModel = hiltViewModel(),
    onSave: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    ProfileEditorStatelessConfectionerScreen(
        state.value,
        onNameChange = { viewModel.updateName(it) },
        onPhoneNumberChange = { viewModel.updatePhoneNumber(it) },
        onEmailChange = { viewModel.updateEmail(it) },
        onAddressChange = { viewModel.updateAddress(it) },
        onDescriptionChange = { viewModel.updateDescription(it) },
        onSave = {
            viewModel.save(onSave)
        },
        topBar = topBar
    )
}

@Preview
@Composable
fun PreviewProfileEditorStatelessConfectionerScreen() {
    ProfileEditorStatelessConfectionerScreen(
        state = ProfileEditorConfectionerState(),
        onNameChange = {},
        onPhoneNumberChange = {},
        onEmailChange = {},
        onAddressChange = {},
        onDescriptionChange = {},
        onSave = {},
        topBar = { TopNameBar("Личные данные") { } }
    )
}