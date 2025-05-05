package dev.tp_94.mobileapp.orders.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import kotlinx.datetime.*

@Composable
fun ConfectionerOrderItem(
    modifier: Modifier = Modifier,
    cakeName: String,
    customersPhone: String,
    orderDate: LocalDate,
    preparation: Int,
    price: Int,
    onDecline: () -> Unit,
    onApprove: () -> Unit,
    onDone: () -> Unit,
    orderStatus: OrderStatus
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //TODO: add Image support
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
                    Text(
                        text = customersPhone,
                        style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                    )
                    if (orderStatus == OrderStatus.IN_PROGRESS || orderStatus == OrderStatus.PENDING_APPROVAL || orderStatus == OrderStatus.PENDING_PAYMENT) {
                        Text(
                            text = "Осталось: ${
                                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                                    .date.daysUntil(orderDate.plus(DatePeriod(days = preparation)))
                            } дней",
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
            //TODO: generalize
            when (orderStatus) {
                OrderStatus.CANCELED -> {
                    Text(
                        text = "Заказ отменён покупателем",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                OrderStatus.RECEIVED -> {
                    Text(
                        text = "Заказ получен",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                OrderStatus.PENDING_PAYMENT -> {
                    Text(
                        text = "Заказ ожидает оплаты",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                OrderStatus.DONE -> {
                    Text(
                        text = "Ожидает получения",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                OrderStatus.PENDING_APPROVAL -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {
                        DiscardButton(
                            onClick = onDecline,
                            modifier = Modifier.width(150.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Отказаться",
                                style = TextStyles.button(colorResource(R.color.middle_text))
                            )
                        }
                        Spacer(Modifier.width(15.dp))
                        ActiveButton(
                            onClick = onApprove,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Принять",
                                style = TextStyles.button(colorResource(R.color.light_background))
                            )
                        }
                    }
                }

                OrderStatus.IN_PROGRESS -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {
                        ActiveButton(
                            onClick = onDone,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Готово",
                                style = TextStyles.button(colorResource(R.color.light_background))
                            )
                        }
                    }
                }

                OrderStatus.REJECTED -> {
                    Text(
                        text = "Отменён кондитером",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewConfectionerOrderItem() {
    ConfectionerOrderItem(
        cakeName = "Наполеон",
        customersPhone = "Кристина-Елена Лебедь",
        orderDate = LocalDate(2025, 5, 3),
        preparation = 5,
        price = 10000,
        onApprove = {},
        onDecline = {},
        onDone = {},
        orderStatus = OrderStatus.PENDING_APPROVAL,
    )
}

@Composable
fun CustomerOrderItem(
    modifier: Modifier = Modifier,
    cakeName: String,
    customersPhone: String,
    orderDate: LocalDate,
    preparation: Int,
    price: Int,
    onDecline: () -> Unit,
    onPay: () -> Unit,
    onReceive: () -> Unit,
    orderStatus: OrderStatus
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                //TODO: add Image support
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
                    Text(
                        text = customersPhone,
                        style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                    )
                    if (orderStatus == OrderStatus.IN_PROGRESS || orderStatus == OrderStatus.PENDING_APPROVAL) {
                        Text(
                            text = "Осталось: ${
                                Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
                                    .date.daysUntil(orderDate.plus(DatePeriod(days = preparation)))
                            } дней",
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
            when (orderStatus) {
                OrderStatus.CANCELED -> {
                    Text(
                        text = "Заказ отменён покупателем",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                OrderStatus.RECEIVED -> {
                    Text(
                        text = "Заказ получен",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                OrderStatus.PENDING_PAYMENT -> {
                    Text(
                        text = "Заказ ожидает оплаты",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp)
                    ) {
                        DiscardButton(
                            onClick = onDecline,
                            modifier = Modifier.width(150.dp),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Отказаться",
                                style = TextStyles.button(colorResource(R.color.middle_text))
                            )
                        }
                        Spacer(Modifier.width(15.dp))
                        ActiveButton(
                            onClick = onPay,
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp)
                        ) {
                            Text(
                                text = "Оплатить",
                                style = TextStyles.button(colorResource(R.color.light_background))
                            )
                        }
                    }
                }

                OrderStatus.DONE -> {
                    Text(
                        text = "Готов к получению",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    ActiveButton(
                        onClick = onReceive,
                        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Получен",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                }

                OrderStatus.PENDING_APPROVAL -> {
                    Text(
                        text = "Ожидает одобрение кондитера...",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_text)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    DiscardButton(
                        onClick = onDecline,
                        modifier = Modifier.fillMaxWidth().padding(top = 15.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            text = "Отменить",
                            style = TextStyles.button(colorResource(R.color.middle_text))
                        )
                    }
                }

                OrderStatus.IN_PROGRESS -> {
                    Text(
                        text = "Готовится...",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_text)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }

                OrderStatus.REJECTED -> {
                    Text(
                        text = "Отменён кондитером",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
                        modifier = Modifier.padding(top = 15.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomerOrderItem() {
    CustomerOrderItem(
        cakeName = "Наполеон",
        customersPhone = "Кристина-Елена Лебедь",
        orderDate = LocalDate(2025, 5, 3),
        preparation = 5,
        price = 10000,
        onDecline = {},
        orderStatus = OrderStatus.PENDING_APPROVAL,
        onPay = {},
        onReceive = {},
    )
}