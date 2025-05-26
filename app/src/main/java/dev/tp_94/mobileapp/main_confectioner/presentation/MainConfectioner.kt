package dev.tp_94.mobileapp.main_confectioner.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.rememberAsyncImagePainter
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.cakes_feed.presentation.components.CakeFeedItem
import dev.tp_94.mobileapp.cakes_feed.presentation.components.CakeFeedItemEditable
import dev.tp_94.mobileapp.confectioner_page.presentation.components.ConfectionerCard
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.custom_order_settings.presentation.components.SectionHeader
import dev.tp_94.mobileapp.main_customer.presentation.MainState

@Composable
fun MainConfectionerStatefulScreen(
    viewModel: MainConfectionerViewModel = hiltViewModel(),
    onMyProfileClick: () -> Unit,
    onCustomOrdersClick: () -> Unit,
    onNavigateToProductEdit: (CakeGeneral) -> Unit,
    onError: () -> Unit,
) {
    val user = viewModel.getUser()
    LaunchedEffect(user) {
        if (user == null || user !is Confectioner) {
            onError()
            viewModel.exit()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    MainConfectionerStatelessScreen(
        state = state,
        onMyProfileClick = onMyProfileClick,
        onCustomOrdersClick = onCustomOrdersClick,
        onEdit = onNavigateToProductEdit,
    )
}

@Composable
fun MainConfectionerStatelessScreen(
    state: MainConfectionerState,
    onMyProfileClick: () -> Unit,
    onEdit: (CakeGeneral) -> Unit,
    onCustomOrdersClick: () -> Unit
) {
    Scaffold { innerPadding ->
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
                    .padding(12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ConfectionerCard(
                    name = state.confectioner.name,
                    address = state.confectioner.address,
                    description = state.confectioner.description,
                    onMyProfileClick = onMyProfileClick,
                    onButtonClick = onCustomOrdersClick,
                    customOrdersText = "Мои заказы",
                )
                Spacer(modifier = Modifier.width(8.dp))
                SectionHeader("Каталог")
                Spacer(modifier = Modifier.width(8.dp))
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(state.products) { product ->
                        CakeFeedItemEditable(
                            name = product.name,
                            weight = product.weight,
                            preparation = product.preparation,
                            price = product.price,
                            onOpen = { onEdit(product) },
                            image = rememberAsyncImagePainter(product.imageUrl)
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMainConfectionerStatelessScreen() {
    MainConfectionerStatelessScreen(
        state = MainConfectionerState(
            confectioner = Confectioner(
                id = 1,
                name = "TODO()",
                phoneNumber = "TODO()",
                email = "TODO()",
                description = "TODO()",
                address = "TODO()"
            ),
            products = arrayListOf(
                CakeGeneral(
                    price = 1000,
                    name = "TODO(1)",
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
        onMyProfileClick = { TODO() },
        onCustomOrdersClick = { TODO() },
        onEdit = { TODO() },
    )
}
