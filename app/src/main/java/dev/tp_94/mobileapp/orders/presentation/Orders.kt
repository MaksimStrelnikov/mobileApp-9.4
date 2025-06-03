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
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.orders.presentation.components.OrderItem
import dev.tp_94.mobileapp.orders.presentation.components.PriceOfferEditor
import dev.tp_94.mobileapp.orders.presentation.components.UserType

@Composable
fun ConfectionerOrdersStatefulScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
    onNavigate: (Order) -> Unit,
    onError: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val session = viewModel.session.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (session.value == null || session.value!!.user !is Confectioner) {
            onError()
            viewModel.exit()
        }
        viewModel.getOrders()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    ConfectionerOrdersStatelessScreen(
        state = state,
        onReject = { viewModel.changeStatus(it, OrderStatus.REJECTED) },
        onApprove = { price ->
            viewModel.changeStatus(
                price,
                OrderStatus.PENDING_PAYMENT
            )
        },
        onDone = { viewModel.changeStatus(it, OrderStatus.DONE) },
        onOpenPriceEditor = { viewModel.onDialogOpen(it) },
        onClosePriceEditor = { viewModel.onDialogClose() },
        onClick = onNavigate,
        topBar = topBar,
    )
}

@Composable
fun ConfectionerOrdersStatelessScreen(
    state: OrdersState,
    onClick: (Order) -> Unit,
    onOpenPriceEditor: (Order) -> Unit,
    onClosePriceEditor: () -> Unit,
    onReject: (Order) -> Unit,
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
                    OrderItem(
                        cakeName = order.cake.name,
                        image = if (order.cake is CakeGeneral) order.cake.imageUrl?.let {
                            rememberAsyncImagePainter(
                                it
                            )
                        } else null,
                        customersPhone = order.customer.phoneNumber,
                        orderDate = order.date,
                        preparation = order.cake.preparation,
                        price = order.price,
                        onReject = { onReject(order) },
                        onApprove = {
                            onOpenPriceEditor(order)
                        },
                        onDone = { onDone(order) },
                        orderStatus = order.orderStatus,
                        userType = UserType.CONFECTIONER,
                        onPay = {},
                        onReceive = {},
                        onCancel = {},
                        onClick = { onClick(order) }
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
        onReject = { TODO() },
        onApprove = { TODO() },
        onDone = { TODO() },
        topBar = { TopNameBar("F") { } },
        onOpenPriceEditor = { TODO() },
        onClosePriceEditor = { TODO() },
        onClick = { TODO() }
    )
}

@Composable
fun CustomerOrdersStatefulScreen(
    viewModel: OrdersViewModel = hiltViewModel(),
    onNavigate: (Order) -> Unit,
    onError: () -> Unit,
    onPay: (Order) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val session = viewModel.session.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (session.value == null || session.value!!.user !is Customer) {
            onError()
            viewModel.exit()
        }
        viewModel.getOrders()
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    CustomerOrdersStatelessScreen(
        state = state,
        onCancel = { viewModel.changeStatus(it, OrderStatus.CANCELED) },
        onPay = { onPay(it) },
        onReceive = { viewModel.changeStatus(it, OrderStatus.RECEIVED) },
        topBar = topBar,
        bottomBar = bottomBar,
        onClick = onNavigate
    )
}

@Composable
fun CustomerOrdersStatelessScreen(
    state: OrdersState,
    onClick: (Order) -> Unit,
    onCancel: (Order) -> Unit,
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
                    OrderItem(
                        cakeName = order.cake.name,
                        image = if (order.cake is CakeGeneral) order.cake.imageUrl?.let {
                            rememberAsyncImagePainter(
                                it
                            )
                        } else null,
                        customersPhone = order.confectioner.phoneNumber,
                        orderDate = order.date,
                        preparation = order.cake.preparation,
                        price = order.price,
                        onCancel = { onCancel(order) },
                        orderStatus = order.orderStatus,
                        onPay = { onPay(order) },
                        onReceive = { onReceive(order) },
                        userType = UserType.CUSTOMER,
                        onApprove = { },
                        onDone = { },
                        onReject = { },
                        onClick = { onClick(order) }
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
        onCancel = { TODO() },
        topBar = { TopNameBar("F") { } },
        onPay = { TODO() },
        onReceive = { TODO() },
        bottomBar = { TODO() },
        onClick = { TODO() }
    )
}