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
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.orders.presentation.components.GeneralOrderItem

@Composable
fun OrdersStatelessScreen(
    state: OrdersState,
    onDecline: (Order) -> Unit,
    onApprove: (Order) -> Unit,
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
                items(state.orders) {
                    GeneralOrderItem(
                        cakeName = it.cake.name,
                        customerName = it.customer.phoneNumber,
                        orderDate = it.date,
                        preparation = it.cake.preparation,
                        price = it.price,
                        onDecline = {onDecline(it)},
                        onApprove = {onApprove(it)},
                        onDone = {onDone(it)},
                        orderStatus = it.orderStatus
                    )
                    Spacer(Modifier.height(12.dp))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewOrdersStatelessScreen() {
    OrdersStatelessScreen(
        state = OrdersState(
            orders = arrayListOf()
        ),
        onDecline = { TODO() },
        onApprove = { TODO() },
        onDone = { TODO() },
        topBar = { TopNameBar("F") { } }
    )
}