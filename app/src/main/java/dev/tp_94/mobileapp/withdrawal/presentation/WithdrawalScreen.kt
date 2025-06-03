package dev.tp_94.mobileapp.withdrawal.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.PriceEditor
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.payment.presentation.components.CardsList
import dev.tp_94.mobileapp.withdrawal.presentation.components.WithdrawalSum

@Composable
fun WithdrawalStatefulScreen(
    viewModel: WithdrawalViewModel = hiltViewModel(),
    onAddNewCard: () -> Unit,
    onSuccessfulWithdrawal: () -> Unit,
    onErrorWithdrawal: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.update()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    WithdrawalStatelessScreen(
        state = state,
        actionButtonName = "Вывести",
        onSumChange = { viewModel.onSumChange(it) },
        onSelect = { viewModel.onSelect(it) },
        onAddNewCard = onAddNewCard,
        onWithdraw = { card, sum ->
            viewModel.onWithdraw(
                card,
                sum,
                onSuccessfulWithdrawal,
                onErrorWithdrawal
            )
        },
        topBar = topBar
    )
}

@Composable
fun WithdrawalStatelessScreen(
    state: WithdrawalState,
    actionButtonName: String,
    onSumChange: (Int) -> Unit,
    onSelect: (Card?) -> Unit,
    onAddNewCard: () -> Unit,
    onWithdraw: (Card, Int) -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = {
            //TODO: generalize bottom payment bar
            ActiveButton(
                onClick = {
                    if (state.selected == null) onAddNewCard() else onWithdraw(
                        state.selected,
                        state.sum
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(6.dp)
            ) {
                Text(
                    text = if (state.selected == null) "Привязать карту" else actionButtonName,
                    style = TextStyles.button(color = colorResource(R.color.light_background)),
                    modifier = Modifier
                        .padding(12.dp)
                )
            }
        }

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
                    .fillMaxWidth()
                    .padding(horizontal = 22.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                WithdrawalSum(
                    canWithdraw = state.available.toString(),
                    inProcess = state.inProgress.toString(),
                )
                Spacer(Modifier.height(8.dp))
                PriceEditor(
                    text = if (state.sum == 0) "" else state.sum.toString(),
                    onChange = {
                        if (it.isEmpty()) {
                            onSumChange(0)
                        } else {
                            onSumChange(it.toIntOrNull() ?: 0)
                        }
                    },
                    defaultText = "Сумма вывода",
                    backgroundColor = colorResource(R.color.dark_background)
                )
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(
                    color = colorResource(R.color.light_text),
                    thickness = 2.dp
                )
                Spacer(Modifier.height(8.dp))
                CardsList(
                    cardList = state.cards,
                    selected = state.selected,
                    onSelect = onSelect,
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewWithdrawalStatelessScreen() {
    var state by remember { mutableStateOf(WithdrawalState(available = 10000)) }
    WithdrawalStatelessScreen(
        state = state,
        actionButtonName = "Вывести",
        onSumChange = { state = state.copy(sum = it) },
        onSelect = {},
        onAddNewCard = {},
        onWithdraw = { _, _ -> },
        topBar = { TopNameBar("Кукушка") { } }
    )
}