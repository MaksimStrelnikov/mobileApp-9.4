package dev.tp_94.mobileapp.basket.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.basket.presentation.components.ProductItem
import dev.tp_94.mobileapp.basket.presentation.components.TotalSum
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar

@Composable
fun BasketStatefulScreen(
    viewModel: BasketViewModel = hiltViewModel(),
    onOrder: (List<CakeGeneral>) -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                viewModel.update()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    val state by viewModel.state.collectAsStateWithLifecycle()
    BasketStatelessScreen(
        state = state,
        onAdd = { viewModel.add(it) },
        onRemove = { viewModel.remove(it) },
        onOrder = { onOrder(state.items) },
        onClear = { viewModel.clearBasket() },
        topBar = topBar,
        bottomBar = bottomBar
    )
}

@Composable
fun BasketStatelessScreen(
    state: BasketState,
    onAdd: (CakeGeneral) -> Unit,
    onRemove: (CakeGeneral) -> Unit,
    onClear: () -> Unit,
    onOrder: () -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(
                    color = colorResource(R.color.background)
                )
        ) {
            val map = state.items.groupingBy { it }.eachCount()
            var sum = 0
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Box(
                    modifier = Modifier.fillMaxWidth(),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    val interactionSource = remember { MutableInteractionSource() }

                    Row(
                        modifier = Modifier
                            .clip(RoundedCornerShape(12.dp))
                            .clickable(
                                interactionSource = interactionSource,
                                indication = rememberRipple(bounded = true),
                                onClick = onClear
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Text(
                            text = "УДАЛИТЬ ВСЕ",
                            style = TextStyles.regular(colorResource(R.color.light_text))
                        )
                        Icon(
                            painter = painterResource(R.drawable.bin),
                            contentDescription = "Clear",
                            tint = colorResource(R.color.light_text)
                        )
                    }
                }

                Spacer(Modifier.height(8.dp))
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(map.entries.toList()) { (cake, amount) ->
                        ProductItem(
                            cake = cake,
                            image = cake.imageUrl?.let {
                                rememberAsyncImagePainter(it)
                            },
                            amount = amount,
                            onAdd = { onAdd(cake) },
                            onRemove = { onRemove(cake) }
                        )
                        sum += cake.price * amount
                        Spacer(Modifier.height(6.dp))
                    }
                    item {
                        Spacer(Modifier.height(6.dp))
                        HorizontalDivider(
                            thickness = 2.dp,
                            color = colorResource(R.color.light_text)
                        )
                        Spacer(Modifier.height(12.dp))
                        TotalSum(sum = sum)
                        Spacer(Modifier.height(24.dp))
                        ActiveButton(
                            onClick = onOrder,
                            enabled = state.items.isNotEmpty(),
                            modifier = Modifier.fillMaxWidth(),
                        ) {
                            Text(
                                text = "Заказать",
                                style = TextStyles.button(colorResource(R.color.light_background))
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewBasketStatelessScreen() {
    BasketStatelessScreen(
        state = BasketState(
            listOf(
                CakeGeneral(
                    id = 1,
                    price = 1000,
                    imageUrl = "https://caker.ralen.top/assets/mock_cake.png",
                    name = "TODO()",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 3,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO()",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()",
                        canWithdrawal = 0,
                        inProcess = 0
                    )
                ),
                CakeGeneral(
                    id = 2,
                    price = 1000,
                    imageUrl = "https://caker.ralen.top/assets/mock_cake2.png",
                    name = "TODO()",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 3,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO()",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()",
                        canWithdrawal = 0,
                        inProcess = 0
                    )
                ),
                CakeGeneral(
                    id = 3,
                    price = 1000,
                    imageUrl = "https://caker.ralen.top/assets/mock_cake2.png",
                    name = "TODO()",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 3,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO()",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()",
                        canWithdrawal = 0,
                        inProcess = 0
                    )
                ),
                CakeGeneral(
                    id = 4,
                    price = 1000,
                    imageUrl = "https://caker.ralen.top/assets/mock_cake2.png",
                    name = "TODO()",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 3,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO()",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()",
                        canWithdrawal = 0,
                        inProcess = 0
                    )
                ),
                CakeGeneral(
                    id = 5,
                    price = 1000,
                    imageUrl = "https://caker.ralen.top/assets/mock_cake2.png",
                    name = "TODO()",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 3,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO()",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()",
                        canWithdrawal = 0,
                        inProcess = 0
                    )
                )
            )
        ),
        onAdd = {},
        onRemove = {},
        onClear = {},
        onOrder = {},
        topBar = { TopNameBar("Корзина") { } },
        bottomBar = {
            BottomNavBar(
                onMainClick = { TODO() },
                onOrdersClick = { TODO() },
                onBasketClick = { TODO() },
                onProfileClick = { TODO() },
                currentScreen = Screen.BASKET
            )
        }
    )
}