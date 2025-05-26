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
import dev.tp_94.mobileapp.orders.presentation.components.UserType
import dev.tp_94.mobileapp.order_view.presentation.components.ConfectionerBubble
import dev.tp_94.mobileapp.order_view.presentation.components.PhoneText
import dev.tp_94.mobileapp.order_view.presentation.components.TextPart
import dev.tp_94.mobileapp.order_view.presentation.components.TextPartWithPairs
import dev.tp_94.mobileapp.orders.presentation.components.PriceOfferEditor
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingAddUneditable
import kotlinx.datetime.LocalDate

@Composable
fun OrderViewStatefulScreen(
    viewModel: OrderViewModel = hiltViewModel(),
    onConfectionerClick: (Confectioner) -> Unit,
    onPay: (Order) -> Unit,
    onError: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val user = viewModel.getUser()
    LaunchedEffect(user) {
        if (user == null || (user !is Confectioner && user !is Customer)) {
            onError()
        }
    }
    val userType = if (user is Confectioner) UserType.CONFECTIONER else UserType.CUSTOMER
    val state by viewModel.state.collectAsStateWithLifecycle()
    when (state.order.cake) {
        is CakeCustom -> CustomOrderViewStatelessScreen(
            state = state,
            userType = userType,
            onConfectionerClick = onConfectionerClick,
            onReceive = { viewModel.changeStatus(OrderStatus.RECEIVED) },
            onCancel = { viewModel.changeStatus(OrderStatus.CANCELED) },
            onReject = { viewModel.changeStatus(OrderStatus.REJECTED) },
            onPay = { onPay(state.order) },
            onApprove = { viewModel.changeStatus(it, OrderStatus.PENDING_PAYMENT) },
            onDone = { viewModel.changeStatus(OrderStatus.DONE) },
            topBar = topBar,
            onClosePriceEditor = { viewModel.onDialogClose() },
            onOpenPriceEditor = { viewModel.onDialogOpen() },
        )

        is CakeGeneral -> GeneralOrderViewStatelessScreen(
            state = state,
            image = null,
            userType = userType,
            onConfectionerClick = onConfectionerClick,
            onReceive = { viewModel.changeStatus(OrderStatus.RECEIVED) },
            onCancel = { viewModel.changeStatus(OrderStatus.CANCELED) },
            onReject = { viewModel.changeStatus(OrderStatus.REJECTED) },
            onPay = { onPay(state.order) },
            onApprove = { viewModel.changeStatus(OrderStatus.PENDING_PAYMENT) },
            onDone = { viewModel.changeStatus(OrderStatus.DONE) },
            topBar = topBar
        )
    }
}

@Composable
fun GeneralOrderViewStatelessScreen(
    state: OrderState,
    image: Painter? = null,
    userType: UserType,
    onConfectionerClick: (Confectioner) -> Unit,
    onReceive: () -> Unit,
    onCancel: () -> Unit,
    onReject: () -> Unit,
    onPay: () -> Unit,
    onApprove: (Int) -> Unit,
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
                        text = state.order.cake.name,
                        style = TextStyles.header(colorResource(R.color.dark_text))
                    )
                    Spacer(Modifier.height(8.dp))
                    TextPart("Описание", state.order.cake.description)
                    TextPartWithPairs(
                        "Параметры", listOf(
                            Pair("Диаметр изделия", state.order.cake.diameter.toString() + " см"),
                            Pair(
                                "Вес изделия",
                                (state.order.cake as CakeGeneral).weight.toString() + " кг"
                            ),
                            Pair(
                                "Срок изготовления",
                                state.order.cake.preparation.toString() + " дней"
                            )
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
                            text = state.order.customer.name,
                            style = TextStyles.regular(colorResource(R.color.middle_text)),
                            modifier = Modifier.weight(1f)
                        )
                        PhoneText(
                            phoneNumber = "+7" + state.order.customer.phoneNumber,
                            color = colorResource(id = R.color.middle_text)
                        )
                    }
                    Spacer(Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OrderStatusChangeVertical(
                            status = state.order.orderStatus,
                            userType = userType,
                            onReceive = onReceive,
                            onCancel = onCancel,
                            onReject = onReject,
                            onPay = onPay,
                            onApprove = { onApprove(state.order.price) },
                            onDone = onDone,
                        )
                    }
                    ConfectionerBubble(
                        name = state.order.confectioner.name,
                        onClick = {
                            if (userType == UserType.CUSTOMER) onConfectionerClick(
                                state.order.confectioner
                            )
                        })
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
    val state = OrderState(
        Order(
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
            ), confectioner = confectioner
        ),
    )
    GeneralOrderViewStatelessScreen(
        state = state,
        image = painterResource(R.drawable.mock_cake),
        topBar = { TopNameBar("Заказ ы)))") {} },
        onConfectionerClick = {},
        userType = UserType.CUSTOMER,
        onReceive = { TODO() },
        onCancel = { TODO() },
        onPay = { TODO() },
        onApprove = { TODO() },
        onDone = { TODO() },
        onReject = { TODO() },

        )
}

@Composable
fun CustomOrderViewStatelessScreen(
    state: OrderState,
    userType: UserType,
    onConfectionerClick: (Confectioner) -> Unit,
    topBar: @Composable () -> Unit,
    onReceive: () -> Unit,
    onCancel: () -> Unit,
    onReject: () -> Unit,
    onPay: () -> Unit,
    onApprove: (Int) -> Unit,
    onDone: () -> Unit,
    onOpenPriceEditor: () -> Unit,
    onClosePriceEditor: () -> Unit
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
                            (state.order.cake as CakeCustom).color.darken(),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center,
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(
                            modifier = Modifier
                                .size(270.dp)
                                .clip(CircleShape)
                                .background(state.order.cake.color),
                            contentAlignment = Alignment.Center
                        ) {
                            state.order.cake.imageUrl?.let { uri ->
                                val painter = rememberAsyncImagePainter(uri)
                                Image(
                                    painter = painter,
                                    contentDescription = "Выбранное изображение",
                                    modifier = Modifier
                                        .size(200.dp)
                                        .offset { state.order.cake.imageOffset.round() }
                                )
                            }
                            Text(text = state.order.cake.text,
                                style = TextStyles.header(colorResource(R.color.dark_text)),
                                modifier = Modifier
                                    .offset { state.order.cake.textOffset.round() })
                        }
                        Spacer(modifier = Modifier.height(30.dp))
                        Box(
                            modifier = Modifier
                                .width(250.dp)
                                .height(70.dp)
                                .background(state.order.cake.color)
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
                        text = state.order.cake.name,
                        style = TextStyles.header(colorResource(R.color.dark_text))
                    )
                    Spacer(Modifier.height(8.dp))
                    TextPart("Комментарий", state.order.cake.description)
                    if (state.order.price != 0) {
                        TextPartWithPairs(
                            "Параметры", listOf(
                                Pair("Диаметр изделия", state.order.cake.diameter.toString() + " см"),
                                Pair(
                                    "Срок изготовления",
                                    state.order.cake.preparation.toString() + " дней"
                                ),
                                Pair("Цена", state.order.price.toString() + " ₽")
                            )
                        )
                    } else {
                        TextPartWithPairs(
                            "Параметры", listOf(
                                Pair("Диаметр изделия", state.order.cake.diameter.toString() + " см"),
                                Pair(
                                    "Срок изготовления",
                                    state.order.cake.preparation.toString() + " дней"
                                )
                            )
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(rememberScrollState())
                    ) {
                        state.order.cake.fillings.forEach {
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
                            text = state.order.customer.name,
                            style = TextStyles.regular(colorResource(R.color.middle_text)),
                            modifier = Modifier.weight(1f)
                        )
                        PhoneText(
                            phoneNumber = "+7" + state.order.customer.phoneNumber,
                            color = colorResource(id = R.color.middle_text)
                        )
                    }
                    Spacer(Modifier.height(15.dp))
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        OrderStatusChangeVertical(
                            status = state.order.orderStatus,
                            userType = userType,
                            onReceive = onReceive,
                            onCancel = onCancel,
                            onReject = onReject,
                            onPay = onPay,
                            onApprove = onOpenPriceEditor,
                            onDone = onDone,
                        )
                        if (!(state.error == null || state.error == "")) {
                            Text(
                                state.error,
                                style = TextStyles.regular(colorResource(R.color.dark_accent))
                            )
                        }
                    }
                    ConfectionerBubble(
                        name = state.order.confectioner.name,
                        onClick = { if (userType == UserType.CUSTOMER) onConfectionerClick(state.order.confectioner) })
                }
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
    var state = OrderState(
        Order(
            cake = CakeCustom(
                name = "Индивидуальный торт",
                description = "Сделай по красоте от души прошу, реально, чтобы всё чётко было, вкусно, сочно и т.п.",
                diameter = 10f,
                preparation = 2,
                color = Color.Cyan,
                text = "TODO()",
                textOffset = Offset.Zero,
                imageUrl = null,
                imageOffset = Offset.Zero,
                fillings = listOf("Шоколад", "Клубника", "Манго", "Маракуйа", "Ананас"),
                confectioner = confectioner
            ),
            date = LocalDate(2024, 12, 31),
            orderStatus = OrderStatus.PENDING_APPROVAL,
            price = 100000,
            quantity = 2,
            customer = Customer(
                id = 1,
                name = "asd",
                phoneNumber = "TODO()",
                email = "TODO()"
            ),
            confectioner = confectioner
        )
    )
    CustomOrderViewStatelessScreen(
        state = state,
        onConfectionerClick = {},
        userType = UserType.CUSTOMER,
        onReceive = { TODO() },
        onPay = { TODO() },
        onApprove = { state = state.copy(order = state.order.copy(price = it)) },
        onDone = { TODO() },
        topBar = { TopNameBar("Заказ ы)))") {} },
        onCancel = { TODO() },
        onReject = { TODO() },
        onOpenPriceEditor = { state = state.copy(dialogOpen = true) },
        onClosePriceEditor = { state = state.copy(dialogOpen = false) },
    )
}