package dev.tp_94.mobileapp.order_payment.presentation

import android.util.Log
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.order_payment.presentation.components.CardsList
import dev.tp_94.mobileapp.order_payment.presentation.components.TotalCost
import kotlinx.datetime.LocalDate

@Composable
fun OrderPaymentStatefulScreen(
    viewModel: OrderPaymentViewModel = hiltViewModel(),
    actionButtonName: String = "Оплатить",
    onSuccessfulPay: () -> Unit,
    onErrorPay: () -> Unit,
    onAddNewCard: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val state by viewModel.mainState.collectAsStateWithLifecycle()
    OrderPaymentStatelessScreen(
        state = state,
        actionButtonName = actionButtonName,
        onSelect = {
            Log.println(Log.INFO, "Log", "Received $it")
            viewModel.selectCard(it)
            Log.println(Log.INFO, "Log", "Put ${state.selected.toString()}")
        },
        onPay = { card, order ->
            viewModel.onPay(card, order, onSuccessfulPay, onErrorPay)
        },
        onAddNewCard = {
            viewModel.createNewCard()
            onAddNewCard()
        },
        topBar = topBar,
    )
}

@Composable
fun OrderPaymentStatelessScreen(
    state: OrderPaymentState,
    actionButtonName: String,
    onSelect: (Card?) -> Unit,
    onPay: (Card, Order) -> Unit,
    onAddNewCard: () -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = {
            ActiveButton(
                onClick = {
                    if (state.selected == null) onAddNewCard() else onPay(
                        state.selected,
                        state.order
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
                .background(color = colorResource(R.color.background))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(70.dp))
                TotalCost(state.order)
                Spacer(Modifier.height(8.dp))
                HorizontalDivider(
                    color = colorResource(R.color.light_text),
                    thickness = 2.dp
                )
                Spacer(Modifier.height(20.dp))
                Text(
                    text = "Способ оплаты",
                    style = TextStyles.secondHeader(colorResource(R.color.middle_text))
                )
                Spacer(Modifier.height(20.dp))
                CardsList(
                    cardList = state.cards,
                    selected = state.selected,
                    onSelect = {
                        onSelect(it)
                        Log.println(Log.INFO, "Log", "Just selected $it")
                    },
                )
            }

        }
    }
}

@Preview
@Composable
fun PreviewOrderPaymentStatelessScreen() {
    val confectioner = Confectioner(
        id = 1,
        name = "TODO()",
        phoneNumber = "TODO()",
        email = "TODO()",
        description = "TODO()",
        address = "TODO()"
    )
    OrderPaymentStatelessScreen(

        topBar = {
            TopNameBar(
                name = "Оформление заказа"
            ) { }
        },
        state = OrderPaymentState(
            cards = listOf(
                Card(
                    number = "9873378948373487",
                    expirationDate = "12/23",
                    cvcCode = "123"
                )
            ),
            selected = null,
            order = Order(
                cake = CakeCustom(
                    name = "Индивидуальный торт",
                    description = "Сделай по красоте от души прошу, реально, чтобы всё чётко было, вкусно, сочно и т.п.",
                    diameter = 10f,
                    preparation = 2,
                    color = Color.Cyan,
                    text = "TODO()",
                    textOffset = Offset.Zero,
                    imageUri = null,
                    imageOffset = Offset.Zero,
                    fillings = listOf("Шоколад", "Клубника", "Манго", "Маракуйа", "Ананас"),
                ),
                date = LocalDate(2024, 12, 31),
                orderStatus = OrderStatus.REJECTED,
                price = 1000,
                quantity = 2,
                customer = Customer(
                    id = 1,
                    name = "asd",
                    phoneNumber = "TODO()",
                    email = "TODO()"
                ),
                confectioner = confectioner
            ),
        ),
        onSelect = { TODO() },
        onPay = { card, order -> },
        onAddNewCard = { TODO() },
        actionButtonName = "Оплатить"
    )
}