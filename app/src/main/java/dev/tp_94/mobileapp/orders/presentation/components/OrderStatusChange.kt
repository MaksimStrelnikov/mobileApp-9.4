package dev.tp_94.mobileapp.orders.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun OrderStatusChangeVertical(
    status: OrderStatus,
    userType: OrderUserType,
    onReceive: () -> Unit,
    onCancel: () -> Unit,
    onReject: () -> Unit,
    onPay: () -> Unit,
    onApprove: () -> Unit,
    onDone: () -> Unit
) {
    when (status) {
        OrderStatus.CANCELED -> StatusText("Заказ отменён покупателем")
        OrderStatus.RECEIVED -> StatusText("Заказ получен")
        OrderStatus.REJECTED -> StatusText("Отменён кондитером")
        OrderStatus.DONE -> {
            if (userType == OrderUserType.CONFECTIONER) {
                StatusText("Ожидает получения")
            } else {
                StatusText("Готов к получению")
                ActiveButton(
                    onClick = onReceive,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Получен",
                        style = TextStyles.button(colorResource(R.color.light_background))
                    )
                }
            }
        }

        OrderStatus.PENDING_PAYMENT -> {
            StatusText("Заказ ожидает оплаты")
            if (userType == OrderUserType.CUSTOMER) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    DiscardButton(
                        onClick = onCancel,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Отказаться",
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
                            "Оплатить",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                }
            }
        }

        OrderStatus.PENDING_APPROVAL -> {
            if (userType == OrderUserType.CONFECTIONER) {
                Column(Modifier.fillMaxWidth()) {
                    DiscardButton(
                        onClick = onReject,
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Отказаться",
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
                            "Принять",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                }
            } else {
                Text(
                    "Ожидает одобрение кондитера...",
                    style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                )
                DiscardButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Отменить",
                        style = TextStyles.button(colorResource(R.color.middle_text))
                    )
                }
            }
        }

        OrderStatus.IN_PROGRESS -> {
            if (userType == OrderUserType.CONFECTIONER) {
                ActiveButton(
                    onClick = onDone,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Готово",
                        style = TextStyles.button(colorResource(R.color.light_background))
                    )
                }
            } else {
                Text(
                    "Готовится...",
                    style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                )
            }
        }
    }
}

@Composable
fun OrderStatusChangeHorizontal(
    status: OrderStatus,
    userType: OrderUserType,
    onReceive: () -> Unit,
    onCancel: () -> Unit,
    onReject: () -> Unit,
    onPay: () -> Unit,
    onApprove: () -> Unit,
    onDone: () -> Unit
) {
    when (status) {
        OrderStatus.CANCELED -> StatusText("Заказ отменён покупателем")
        OrderStatus.RECEIVED -> StatusText("Заказ получен")
        OrderStatus.REJECTED -> StatusText("Отменён кондитером")
        OrderStatus.DONE -> {
            if (userType == OrderUserType.CONFECTIONER) {
                StatusText("Ожидает получения")
            } else {
                StatusText("Готов к получению")
                ActiveButton(
                    onClick = onReceive,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Получен",
                        style = TextStyles.button(colorResource(R.color.light_background))
                    )
                }
            }
        }

        OrderStatus.PENDING_PAYMENT -> {
            StatusText("Заказ ожидает оплаты")
            if (userType == OrderUserType.CUSTOMER) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp)
                ) {
                    DiscardButton(
                        onClick = onCancel,
                        modifier = Modifier.width(150.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Отказаться",
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
                            "Оплатить",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                }
            }
        }

        OrderStatus.PENDING_APPROVAL -> {
            if (userType == OrderUserType.CONFECTIONER) {
                Row(Modifier.fillMaxWidth()) {
                    DiscardButton(
                        onClick = onReject,
                        modifier = Modifier.width(150.dp),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text(
                            "Отказаться",
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
                            "Принять",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                }
            } else {
                Text(
                    "Ожидает одобрение кондитера...",
                    style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                )
                DiscardButton(
                    onClick = onCancel,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 15.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Отменить",
                        style = TextStyles.button(colorResource(R.color.middle_text))
                    )
                }
            }
        }

        OrderStatus.IN_PROGRESS -> {
            if (userType == OrderUserType.CONFECTIONER) {
                ActiveButton(
                    onClick = onDone,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        "Готово",
                        style = TextStyles.button(colorResource(R.color.light_background))
                    )
                }
            } else {
                Text(
                    "Готовится...",
                    style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                )
            }
        }
    }
}
