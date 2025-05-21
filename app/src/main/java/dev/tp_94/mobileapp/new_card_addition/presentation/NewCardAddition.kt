package dev.tp_94.mobileapp.new_card_addition.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.new_card_addition.presentation.components.CardInputField

@Composable
fun NewCardAdditionStatefulScreen(
    viewModel: NewCardAdditionViewModel = hiltViewModel(),
    onDone: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    NewCardAdditionStatelessScreen(
        state = state,
        onNumberChange = { viewModel.changeNumber(it) },
        onExpirationChange = { viewModel.changeExpiration(it) },
        onCvcCodeChange = { viewModel.changeCvcCode(it) },
        onDone = {
            viewModel.addNewCard(onDone)
        },
        topBar = topBar
    )
}

@Composable
fun NewCardAdditionStatelessScreen(
    state: NewCardAdditionState,
    onNumberChange: (String) -> Unit,
    onExpirationChange: (String) -> Unit,
    onCvcCodeChange: (String) -> Unit,
    onDone: () -> Unit,
    topBar: @Composable () -> Unit,
) {
    Scaffold(
        topBar = topBar
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.background)
                )
        ) {
            Column(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                CardInputField(
                    number = state.number,
                    expiration = state.expiration,
                    cvcCode = state.cvcCode,
                    onNumberChange = onNumberChange,
                    onExpirationChange = onExpirationChange,
                    onCvcCodeChange = onCvcCodeChange,
                )
                Spacer(
                    modifier = Modifier
                        .height(12.dp)
                )
                ActiveButton(
                    onClick = onDone,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Привязать карту",
                        style = TextStyles.button(colorResource(R.color.light_background))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewNewCardAdditionStatelessScreen() {
    NewCardAdditionStatelessScreen(
        state = NewCardAdditionState(
            number = "",
            expiration = "",
            cvcCode = ""
        ),
        onNumberChange = {},
        onExpirationChange = {},
        onCvcCodeChange = {},
        onDone = {},
        topBar = {
            TopNameBar(
                "Привязать новую карту",
                onBackClick = {},
            )
        }
    )
}