package dev.tp_94.mobileapp.product_view.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.themes.BuyButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.order_view.presentation.components.ConfectionerBubble
import dev.tp_94.mobileapp.order_view.presentation.components.TextPart
import dev.tp_94.mobileapp.order_view.presentation.components.TextPartWithPairs
import dev.tp_94.mobileapp.orders.presentation.components.UserType

@Composable
fun ProductViewStatefulScreen(
    viewModel: ProductViewModel = hiltViewModel(),
    onConfectionerClick: (Confectioner) -> Unit,
    onError: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val session = viewModel.session.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (session.value != null && (session.value!!.user !is Customer && session.value!!.user !is Confectioner)) {
            onError()
            viewModel.exit()
        }
    }
    val user = session.value?.user
    val userType = if (user is Confectioner) UserType.CONFECTIONER else if (user is Customer) UserType.CUSTOMER else UserType.UNREGISTERED
    val state by viewModel.state.collectAsStateWithLifecycle()
    GeneralProductViewStatelessScreen(
        state = state,
        image = state.cake.imageUrl?.let { rememberAsyncImagePainter(it) },
        userType = userType,
        onConfectionerClick = onConfectionerClick,
        onBuy = { viewModel.addToBasket(it) },
        topBar = topBar
    )
}

@Composable
fun GeneralProductViewStatelessScreen(
    state: ProductState,
    image: Painter? = null,
    userType: UserType,
    onConfectionerClick: (Confectioner) -> Unit,
    onBuy: ((CakeGeneral) -> Unit)? = null,
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

                if (onBuy != null) {
                    BuyButton(
                        onClick = { onBuy(state.cake) },
                        modifier = Modifier
                            .padding(bottom = 7.dp)
                            .fillMaxWidth()
                            .align(Alignment.Start)
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        text = "от ${state.cake.price} ₽",
                        enabled = userType == UserType.CUSTOMER
                    )
                    Spacer(Modifier.height(12.dp))
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        text = state.cake.name,
                        style = TextStyles.header(colorResource(R.color.dark_text))
                    )
                    Spacer(Modifier.height(8.dp))

                    TextPart("Описание", state.cake.description)

                    TextPartWithPairs(
                        "Параметры", listOf(
                            Pair("Диаметр изделия", "${state.cake.diameter} см"),
                            Pair("Вес изделия", "${state.cake.weight} кг"),
                            Pair("Срок изготовления", "${state.cake.preparation} дней")
                        )
                    )

                    if (userType != UserType.CONFECTIONER) {
                        ConfectionerBubble(
                            name = state.cake.confectioner.name,
                            onClick = { onConfectionerClick(state.cake.confectioner) }
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomerProductViewStatelessScreen() {
    val confectioner = Confectioner(
        id = 1,
        name = "Иван Иванов",
        phoneNumber = "+7 (123) 456-7890",
        email = "ivan@example.com",
        description = "Опытный кондитер с 10-летним стажем",
        address = "Москва, ул. Кондитерская, 15"
    )
    GeneralProductViewStatelessScreen(
        ProductState(
            cake = CakeGeneral(
                price = 1000,
                name = "Наполеон",
                description = "Самый французский торт в России. Был изобретён ещё в далёком 1812 году великим полководцем Кутозовым во время поджога столицы",
                diameter = 10f,
                weight = 2f,
                preparation = 2,
                confectioner = confectioner
            )
        ),
        image = painterResource(R.drawable.mock_cake),
        topBar = { TopNameBar("Товар") {} },
        onConfectionerClick = {},
        onBuy = {},
        userType = UserType.CUSTOMER
    )
}

@Preview
@Composable
fun PreviewConfectionerProductViewStatelessScreen() {
    val confectioner = Confectioner(
        id = 1,
        name = "Иван Иванов",
        phoneNumber = "+7 (123) 456-7890",
        email = "ivan@example.com",
        description = "Опытный кондитер с 10-летним стажем",
        address = "Москва, ул. Кондитерская, 15"
    )
    GeneralProductViewStatelessScreen(
        state = ProductState(
            CakeGeneral(
                price = 1000,
                name = "Наполеон",
                description = "Самый французский торт в России. Был изобретён ещё в далёком 1812 году великим полководцем Кутозовым во время поджога столицы",
                diameter = 10f,
                weight = 2f,
                preparation = 2,
                confectioner = confectioner
            )
        ),
        image = painterResource(R.drawable.mock_cake2),
        onConfectionerClick = {},
        userType = UserType.CONFECTIONER,
        topBar = { TopNameBar("Товар") {} },
    )
}