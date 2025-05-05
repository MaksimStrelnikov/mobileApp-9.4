package dev.tp_94.mobileapp.orders.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.orders.presentation.components.ConfectionerOrderItem
import dev.tp_94.mobileapp.orders.presentation.components.CustomerOrderItem
import dev.tp_94.mobileapp.orders.presentation.components.PriceOfferEditor

@Composable
fun ConfectionerOrdersStatefulScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
    onError: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val user = viewModel.getUser()
    LaunchedEffect(user) {
        if (user == null || user !is Confectioner) {
            onError()
            viewModel.exit()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    ConfectionerOrdersStatelessScreen(
        state = state,
        onDecline = { viewModel.changeStatus(it, OrderStatus.REJECTED) },
        onApprove = { price ->
            viewModel.changeStatus(
                price,
                OrderStatus.PENDING_PAYMENT
            )
        },
        onDone = { viewModel.changeStatus(it, OrderStatus.DONE) },
        onOpenPriceEditor = { viewModel.onDialogOpen(it) },
        onClosePriceEditor = { viewModel.onDialogClose() },
        topBar = topBar,
    )
}

@Composable
fun ConfectionerOrdersStatelessScreen(
    state: OrdersState,
    onOpenPriceEditor: (Order) -> Unit,
    onClosePriceEditor: () -> Unit,
    onDecline: (Order) -> Unit,
    onApprove: (Int) -> Unit,
    onDone: (Order) -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.background))
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(state.orders) { order ->
                    ConfectionerOrderItem(
                        cakeName = order.cake.name,
                        customersPhone = order.customer.phoneNumber,
                        orderDate = order.date,
                        preparation = order.cake.preparation,
                        price = order.price,
                        onDecline = { onDecline(order) },
                        onApprove = {
                            onOpenPriceEditor(order)
                        },
                        onDone = { onDone(order) },
                        orderStatus = order.orderStatus
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
            when {
                state.dialogOpen -> {
                    PriceOfferEditor(
                        onDismiss = onClosePriceEditor,
                        onClick = {
                            onApprove(it)
                            onClosePriceEditor()
                        }
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewConfectionerOrdersStatelessScreen() {
    ConfectionerOrdersStatelessScreen(
        state = OrdersState(
            orders = arrayListOf()
        ),
        onDecline = { TODO() },
        onApprove = { TODO() },
        onDone = { TODO() },
        topBar = { TopNameBar("F") { } },
        onOpenPriceEditor = { TODO() },
        onClosePriceEditor = { TODO() }
    )
}

@Composable
fun CustomerOrdersStatefulScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
    onError: () -> Unit,
    onPay: (Order) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val user = viewModel.getUser()
    LaunchedEffect(user) {
        if (user == null || user !is Customer) {
            onError()
            viewModel.exit()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CustomerOrdersStatelessScreen(
        state = state,
        onDecline = { viewModel.changeStatus(it, OrderStatus.CANCELED) },
        onPay = { onPay(it) },
        onReceive = { viewModel.changeStatus(it, OrderStatus.RECEIVED) },
        topBar = topBar,
        bottomBar = bottomBar
    )
}

@Composable
fun CustomerOrdersStatelessScreen(
    state: OrdersState,
    onDecline: (Order) -> Unit,
    onPay: (Order) -> Unit,
    onReceive: (Order) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(color = colorResource(R.color.background))
                .padding(innerPadding)
        ) {
            LazyColumn {
                items(state.orders) { order ->
                    CustomerOrderItem(
                        cakeName = order.cake.name,
                        customersPhone = order.confectioner.phoneNumber,
                        orderDate = order.date,
                        preparation = order.cake.preparation,
                        price = order.price,
                        onDecline = { onDecline(order) },
                        orderStatus = order.orderStatus,
                        onPay = { onPay(order) },
                        onReceive = { onReceive(order) },
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomerOrdersStatelessScreen() {
    CustomerOrdersStatelessScreen(
        state = OrdersState(
            orders = arrayListOf()
        ),
        onDecline = { TODO() },
        topBar = { TopNameBar("F") { } },
        onPay = { TODO() },
        onReceive = { TODO() },
        bottomBar = { TODO() },
    )
}