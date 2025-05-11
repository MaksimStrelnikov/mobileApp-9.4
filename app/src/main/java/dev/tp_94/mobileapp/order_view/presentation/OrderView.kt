package dev.tp_94.mobileapp.order_view.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.round
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.darken
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.orders.presentation.components.OrderStatusChangeVertical
import dev.tp_94.mobileapp.orders.presentation.components.OrderUserType
import dev.tp_94.mobileapp.order_view.presentation.components.ConfectionerBubble
import dev.tp_94.mobileapp.order_view.presentation.components.PhoneText
import dev.tp_94.mobileapp.order_view.presentation.components.TextPart
import dev.tp_94.mobileapp.order_view.presentation.components.TextPartWithPairs
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingAddUneditable
import kotlinx.datetime.LocalDate

@Composable
fun OrderViewStatefulScreen(
    viewModel: OrderViewModel = hiltViewModel(),
    onConfectionerClick: (Confectioner) -> Unit,
    onError: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val user = viewModel.getUser()
    LaunchedEffect(user) {
        if (user == null || (user !is Confectioner && user !is Customer)) {
            onError()
        }
    }
    val userType = if (user is Confectioner) OrderUserType.CONFECTIONER else OrderUserType.CUSTOMER
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (state.order.cake) {
        is CakeCustom -> CustomOrderViewStatelessScreen(
            order = state.order,
            userType = userType,
            onConfectionerClick = onConfectionerClick,
            onReceive = { viewModel.changeStatus(OrderStatus.RECEIVED) },
            onCancel = { viewModel.changeStatus(OrderStatus.CANCELED) },
            onReject = { viewModel.changeStatus(OrderStatus.REJECTED) },
            onPay = { viewModel.changeStatus(OrderStatus.IN_PROGRESS) },
            onApprove = { viewModel.changeStatus(OrderStatus.PENDING_PAYMENT) },
            onDone = { viewModel.changeStatus(OrderStatus.DONE) },
            topBar = topBar,
        )

        is CakeGeneral -> GeneralOrderViewStatelessScreen(
            order = state.order,
            image = null,
            userType = userType,
            onConfectionerClick = onConfectionerClick,
            onReceive = { viewModel.changeStatus(OrderStatus.RECEIVED) },
            onCancel = { viewModel.changeStatus(OrderStatus.CANCELED) },
            onReject = { viewModel.changeStatus(OrderStatus.REJECTED) },
            onPay = { viewModel.changeStatus(OrderStatus.IN_PROGRESS) },
            onApprove = { viewModel.changeStatus(OrderStatus.PENDING_PAYMENT) },
            onDone = { viewModel.changeStatus(OrderStatus.DONE) },
            topBar = topBar
        )
    }
}

@Composable
fun GeneralOrderViewStatelessScreen(
    order: Order,
    image: Painter? = null,
    userType: OrderUserType,
    onConfectionerClick: (Confectioner) -> Unit,
    onReceive: () -> Unit,
    onCancel: () -> Unit,
    onReject: () -> Unit,
    onPay: () -> Unit,
    onApprove: () -> Unit,
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
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (image == null) {
                    Box(
                        modifier = Modifier
                            .size(360.dp)
                            .background(
                                color = colorResource(R.color.dark_background),
                                shape = RoundedCornerShape(8.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.nocake),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp),
                            tint = colorResource(R.color.light_text)
                        )
                    }
                } else {
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier
                            .size(360.dp)
                            .clip(shape = RoundedCornerShape(8.dp))
                    )
                }
                Spacer(Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = order.cake.name,
                        style = TextStyles.header(colorResource(R.color.dark_text))
                    )
                    Spacer(Modifier.height(8.dp))
                    TextPart("Описание", order.cake.description)
                    TextPartWithPairs(
                        "Параметры", listOf(
                            Pair("Диаметр изделия", order.cake.diameter.toString() + " см"),
                            Pair(
                                "Вес изделия",
                                (order.cake as CakeGeneral).weight.toString() + " кг"
                            ),
                            Pair("Срок изготовления", order.cake.preparation.toString() + " дней")
                        )
                    )
                    Text(
                        text = "Заказчик",
                        style = TextStyles.regular(
                            colorResource(R.color.dark_text),
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(bottom = 11.dp)
                    )
                    Row(
                        modifier = Modifier.padding(bottom = 11.dp)
                    ) {
                        Text(
                            text = order.customer.name,
                            style = TextStyles.regular(colorResource(R.color.middle_text)),
                            modifier = Modifier.weight(1f)
                        )
                        PhoneText(
                            phoneNumber = "+7" + order.customer.phoneNumber,
                            color = colorResource(id = R.color.middle_text)
                        )
                    }
                    Spacer(Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OrderStatusChangeVertical(
                            status = order.orderStatus,
                            userType = userType,
                            onReceive = onReceive,
                            onCancel = onCancel,
                            onReject = onReject,
                            onPay = onPay,
                            onApprove = onApprove,
                            onDone = onDone,
                        )
                    }
                    ConfectionerBubble(
                        name = order.confectioner.name,
                        onClick = { if (userType == OrderUserType.CUSTOMER) onConfectionerClick(order.confectioner) })
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGeneralOrderViewStatelessScreen() {
    val confectioner = Confectioner(
        id = 1,
        name = "TODO()",
        phoneNumber = "TODO()",
        email = "TODO()",
        description = "TODO()",
        address = "TODO()"
    )
    GeneralOrderViewStatelessScreen(
        order = Order(
            cake = CakeGeneral(
                price = 1000,
                name = "Наполеон",
                description = "Самый французский торт в России. Был изобретён ещё в далёком 1812 году великим полководцем Кутозовым во время поджога столицы",
                diameter = 10f,
                weight = 2f,
                preparation = 2,
                confectioner = confectioner
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
        ), image = painterResource(R.drawable.mock_cake), topBar = { TopNameBar("Заказ ы)))") {} },
        onConfectionerClick = {},
        userType = OrderUserType.CUSTOMER,
        onReceive = { TODO() },
        onCancel = { TODO() },
        onPay = { TODO() },
        onApprove = { TODO() },
        onDone = { TODO() },
        onReject = { TODO() }
    )
}

@Composable
fun CustomOrderViewStatelessScreen(
    order: Order,
    userType: OrderUserType,
    onConfectionerClick: (Confectioner) -> Unit,
    topBar: @Composable () -> Unit,
    onReceive: () -> Unit,
    onCancel: () -> Unit,
    onReject: () -> Unit,
    onPay: () -> Unit,
    onApprove: () -> Unit,
    onDone: () -> Unit
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
                    .fillMaxWidth()
                    .padding(horizontal = 26.dp)
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(8.dp))
                //TODO: move to relative offset rather then absolute
                Box(
                    modifier = Modifier
                        .width(360.dp)
                        .height(460.dp)
                        .background(
                            (order.cake as CakeCustom).color.darken(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(270.dp)
                                .clip(CircleShape)
                                .background(order.cake.color),
                            contentAlignment = Alignment.Center
                        ) {
                            order.cake.imageUri?.let { uri ->
                                val painter = rememberAsyncImagePainter(uri)
                                Image(
                                    painter = painter,
                                    contentDescription = "Выбранное изображение",
                                    modifier = Modifier
                                        .size(200.dp)
                                        .offset { order.cake.imageOffset.round() }
                                )
                            }
                            Text(text = order.cake.text,
                                style = TextStyles.header(colorResource(R.color.dark_text)),
                                modifier = Modifier
                                    .offset { order.cake.textOffset.round() })
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Box(
                            modifier = Modifier
                                .width(250.dp)
                                .height(70.dp)
                                .background(order.cake.color)
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = order.cake.name,
                        style = TextStyles.header(colorResource(R.color.dark_text))
                    )
                    Spacer(Modifier.height(8.dp))
                    TextPart("Комментарий", order.cake.description)
                    TextPartWithPairs(
                        "Параметры", listOf(
                            Pair("Диаметр изделия", order.cake.diameter.toString() + " см"),
                            Pair("Срок изготовления", order.cake.preparation.toString() + " дней")
                        )
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        order.cake.fillings.forEach {
                            FillingAddUneditable(
                                text = it
                            )
                            Spacer(Modifier.width(12.dp))
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        text = "Заказчик",
                        style = TextStyles.regular(
                            colorResource(R.color.dark_text),
                            fontWeight = FontWeight.SemiBold
                        ),
                        modifier = Modifier.padding(bottom = 11.dp)
                    )
                    Row(
                        modifier = Modifier.padding(bottom = 11.dp)
                    ) {
                        Text(
                            text = order.customer.name,
                            style = TextStyles.regular(colorResource(R.color.middle_text)),
                            modifier = Modifier.weight(1f)
                        )
                        PhoneText(
                            phoneNumber = "+7" + order.customer.phoneNumber,
                            color = colorResource(id = R.color.middle_text)
                        )
                    }
                    Spacer(Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OrderStatusChangeVertical(
                            status = order.orderStatus,
                            userType = userType,
                            onReceive = onReceive,
                            onCancel = onCancel,
                            onReject = onReject,
                            onPay = onPay,
                            onApprove = onApprove,
                            onDone = onDone,
                        )
                    }
                    ConfectionerBubble(
                        name = order.confectioner.name,
                        onClick = { if (userType == OrderUserType.CUSTOMER) onConfectionerClick(order.confectioner) })
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomOrderViewStatelessScreen() {
    val confectioner = Confectioner(
        id = 1,
        name = "TODO()",
        phoneNumber = "TODO()",
        email = "TODO()",
        description = "TODO()",
        address = "TODO()"
    )
    CustomOrderViewStatelessScreen(
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
        onConfectionerClick = {},
        userType = OrderUserType.CUSTOMER,
        onReceive = { TODO() },
        onPay = { TODO() },
        onApprove = { TODO() },
        onDone = { TODO() },
        topBar = { TopNameBar("Заказ ы)))") {} },
        onCancel = { TODO() },
        onReject = { TODO() },
    )
}