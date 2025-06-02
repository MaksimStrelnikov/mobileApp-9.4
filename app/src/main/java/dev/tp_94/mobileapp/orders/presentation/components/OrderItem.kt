package dev.tp_94.mobileapp.orders.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.order_view.presentation.components.PhoneText
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.plus
import kotlinx.datetime.toLocalDateTime

enum class UserType {
    CONFECTIONER, CUSTOMER, UNREGISTERED
}

@Composable
fun OrderItem(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    cakeName: String,
    customersPhone: String,
    orderDate: LocalDate,
    preparation: Int,
    price: Int,
    orderStatus: OrderStatus,
    userType: UserType,
    onCancel: () -> Unit,
    onReject: () -> Unit,
    onApprove: () -> Unit = {},
    onDone: () -> Unit = {},
    onPay: () -> Unit = {},
    onReceive: () -> Unit = {}
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onClick)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(
                            color = colorResource(R.color.dark_background),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.nocake),
                        contentDescription = null,
                        modifier = Modifier.size(50.dp),
                        tint = colorResource(R.color.light_text)
                    )
                }
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                ) {
                    Text(
                        text = cakeName,
                        style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                    )
                    PhoneText(
                        phoneNumber = "+7$customersPhone",
                        color = colorResource(R.color.dark_text),
                        fontSize = 20.sp
                    )
                    if (orderStatus in listOf(
                            OrderStatus.IN_PROGRESS,
                            OrderStatus.PENDING_APPROVAL,
                            OrderStatus.PENDING_PAYMENT
                        )
                    ) {
                        val daysLeft = Clock.System.now()
                            .toLocalDateTime(TimeZone.currentSystemDefault()).date
                            .daysUntil(orderDate.plus(DatePeriod(days = preparation)))
                        Text(
                            text = "Осталось: $daysLeft дней",
                            style = TextStyles.secondHeader(colorResource(R.color.dark_accent))
                        )
                    }
                    if (orderStatus != OrderStatus.PENDING_APPROVAL) {
                        Text(
                            text = "$price ₽",
                            style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                        )
                    }
                }
            }

            Spacer(Modifier.height(15.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                OrderStatusChangeHorizontal(
                    status = orderStatus,
                    userType = userType,
                    onReceive = onReceive,
                    onCancel = onCancel,
                    onReject = onReject,
                    onPay = onPay,
                    onApprove = onApprove,
                    onDone = onDone,
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomerOrderItem() {
    OrderItem(
        cakeName = "Наполеон",
        customersPhone = "9042865641",
        orderDate = LocalDate(2025, 5, 3),
        preparation = 5,
        price = 10000,
        orderStatus = OrderStatus.PENDING_APPROVAL,
        userType = UserType.CUSTOMER,
        onCancel = {},
        onReject = {},
        onApprove = { TODO() },
        onDone = { TODO() },
        onPay = { TODO() },
        onReceive = { TODO() },
        onClick = { TODO() }
    )
}

@Preview
@Composable
fun PreviewConfectionerOrderItem() {
    OrderItem(
        cakeName = "Наполеон",
        customersPhone = "9042865641",
        orderDate = LocalDate(2025, 5, 3),
        preparation = 5,
        price = 10000,
        orderStatus = OrderStatus.PENDING_APPROVAL,
        userType = UserType.CONFECTIONER,
        onCancel = {},
        onReject = {},
        onApprove = { TODO() },
        onDone = { TODO() },
        onPay = { TODO() },
        onReceive = { TODO() },
        onClick = { TODO() }
    )
}