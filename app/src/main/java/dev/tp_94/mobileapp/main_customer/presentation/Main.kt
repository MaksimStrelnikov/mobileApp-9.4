package dev.tp_94.mobileapp.main_customer.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import coil.imageLoader
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.cakes_feed.presentation.components.CakeFeedItem
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.customers_feed.presentation.components.ConfectionerFeedItem

@Composable
fun MainStatefulScreen(
    viewModel: MainViewModel = hiltViewModel(),
    onError: () -> Unit,
    onSearch: (String) -> Unit,
    onNavigateToConfectioners: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToConfectioner: (Confectioner) -> Unit,
    onNavigateToProduct: (CakeGeneral) -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val session = viewModel.session.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (session.value != null && session.value!!.user !is Customer) {
            onError()
            viewModel.exit()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainStatelessScreen(
        state = state,
        onSearchTextChange = { viewModel.updateSearchText(it) },
        onSearch = { onSearch(state.search) },
        onNavigateToConfectioners = onNavigateToConfectioners,
        onNavigateToProducts = onNavigateToProducts,
        onNavigateToConfectioner = onNavigateToConfectioner,
        onNavigateToProduct = onNavigateToProduct,
        onProductBuyEnabled = session.value != null,
        onProductBuy = { viewModel.addToBasket(it) },
        bottomBar = bottomBar
    )
}

@Composable
fun MainStatelessScreen(
    state: MainState,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onNavigateToConfectioners: () -> Unit,
    onNavigateToProducts: () -> Unit,
    onNavigateToConfectioner: (Confectioner) -> Unit,
    onNavigateToProduct: (CakeGeneral) -> Unit,
    onProductBuyEnabled: Boolean = true,
    onProductBuy: (CakeGeneral) -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    Scaffold(
        topBar = { TopNameBar(name = "Главная", onBackClick = null)  },
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
            LazyColumn(
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth()
            ) {
                /*
                item {
                    SearchInput(
                        text = state.search,
                        defaultText = "Поиск по кондитерам",
                        onChange = onSearchTextChange,
                        onSearch = onSearch
                    )
                    Spacer(Modifier.height(8.dp))
                }
                */
                item {
                    val painter = painterResource(R.drawable.banner)
                    Image(
                        painter = painter,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(
                                painter.intrinsicSize.width / painter.intrinsicSize.height
                            )
                    )
                    Spacer(Modifier.height(8.dp))
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Кондитеры",
                            style = TextStyles.header(colorResource(R.color.dark_text)),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onNavigateToConfectioners
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.next),
                                contentDescription = null,
                                tint = colorResource(R.color.dark_text)
                            )
                        }
                    }
                }
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                    ) {
                        items(state.confectioners) {
                            ConfectionerFeedItem(
                                name = it.name,
                                avatar = null,
                                imageFirst = null,
                                imageSecond = null,
                                onClick = { onNavigateToConfectioner(it) }
                            )
                            Spacer(Modifier.width(8.dp))
                        }
                    }
                }
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Товары",
                            style = TextStyles.header(colorResource(R.color.dark_text)),
                            modifier = Modifier.weight(1f)
                        )
                        IconButton(
                            onClick = onNavigateToProducts
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.next),
                                contentDescription = null,
                                tint = colorResource(R.color.dark_text)
                            )
                        }
                    }
                }
                items(state.products.chunked(2)) { row ->
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        row.forEach { product ->
                                CakeFeedItem(
                                    modifier = Modifier.weight(1f),
                                    name = product.name,
                                    weight = product.weight,
                                    preparation = product.preparation,
                                    price = product.price,
                                    onBuyEnabled = onProductBuyEnabled,
                                    onBuy = { onProductBuy(product) },
                                    onOpen = { onNavigateToProduct(product) },
                                    image = product.imageUrl?.let { url ->
                                        rememberAsyncImagePainter(
                                            model = url,
                                            imageLoader = LocalContext.current.imageLoader
                                        )
                                    }
                                )
                        }
                        if (row.size < 2) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }

            }
        }
    }
}

@Preview
@Composable
fun PreviewMainStatelessScreen() {
    MainStatelessScreen(
        state = MainState(
            confectioners = arrayListOf(
                Confectioner(
                    id = 1,
                    name = "TODO(1)",
                    phoneNumber = "TODO()",
                    email = "TODO()",
                    description = "TODO()",
                    address = "TODO()"
                ),
                Confectioner(
                    id = 2,
                    name = "TODO(2)",
                    phoneNumber = "TODO()",
                    email = "TODO()",
                    description = "TODO()",
                    address = "TODO()"
                ),
                Confectioner(
                    id = 3,
                    name = "TODO(3)",
                    phoneNumber = "TODO()",
                    email = "TODO()",
                    description = "TODO()",
                    address = "TODO()"
                )
            ),
            products = arrayListOf(
                CakeGeneral(
                    price = 1000,
                    name = "TODO(1)",
                    imageUrl = "https://caker.ralen.top/assets/mock_cake.png",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 1,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO(1)",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()"
                    )
                ),
                CakeGeneral(
                    price = 2300,
                    name = "TODO(2)",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 1,
                    confectioner = Confectioner(
                        id = 2,
                        name = "TODO(2)",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()"
                    )
                ),
                CakeGeneral(
                    price = 10000,
                    name = "TODO(1)",
                    imageUrl = "https://caker.ralen.top/assets/mock_cake.png",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 1,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO(1)",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()"
                    )
                ),
                CakeGeneral(
                    price = 2850,
                    name = "TODO(2)",
                    description = "TODO()",
                    diameter = 1f,
                    weight = 1f,
                    preparation = 1,
                    confectioner = Confectioner(
                        id = 2,
                        name = "TODO(2)",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()"
                    )
                )
            )
        ),
        onSearchTextChange = {},
        onSearch = { },
        bottomBar = {
            BottomNavBar(
                onMainClick = { },
                onOrdersClick = { },
                onBasketClick = { },
                onProfileClick = { },
                currentScreen = Screen.MAIN
            )
        },
        onNavigateToConfectioners = {},
        onNavigateToConfectioner = { },
        onProductBuy = {},
        onNavigateToProduct = { },
        onNavigateToProducts = { },
    )
}