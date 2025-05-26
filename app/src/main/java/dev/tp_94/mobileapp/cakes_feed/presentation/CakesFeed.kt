package dev.tp_94.mobileapp.cakes_feed.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.cakes_feed.presentation.components.CakeFeedItem
import dev.tp_94.mobileapp.core.models.Cake
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.customers_feed.presentation.components.SearchInput
import dev.tp_94.mobileapp.customers_feed.presentation.components.SortSelector

@Composable
fun CakesFeedStatefulScreen(
    viewModel: CakesFeedViewModel = hiltViewModel(),
    onNavigate: (Cake) -> Unit,
    onBackClick: () -> Unit,
    onError: () -> Unit
) {
    LaunchedEffect(Unit) {
        if (viewModel.getUser() is Confectioner) {
            onError()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CakesFeedStatelessScreen(
        state = state,
        onSearchTextChange = { viewModel.updateSearchText(it) },
        onSearch = { viewModel.search() },
        onOpen = onNavigate,
        isLoading = state.isLoading,
        onLoadMore = { viewModel.loadMore() },
        topBar = {
            TopNameBar(
                name = "Поиск тортов",
                onBackClick = onBackClick
            )
        },
        onBuy = { viewModel.buy(it) },
        onSortSelected = { viewModel.selectSort(it) },
        bottomBar = { },
    )
}

@Composable
fun CakesFeedStatelessScreen(
    state: CakesFeedState,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onOpen: (Cake) -> Unit,
    onBuy: (Cake) -> Unit,
    onSortSelected: (Sorting) -> Unit,
    isLoading: Boolean,
    onLoadMore: () -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val listState = rememberLazyGridState()
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo }
            .collect { visibleItems ->
                val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: return@collect
                if (lastVisibleItemIndex >= state.feed.size - 3 && !isLoading) {
                    onLoadMore()
                }
            }
    }

    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar,
        containerColor = colorResource(R.color.background)
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(12.dp, 10.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                SearchInput(
                    text = state.searchText,
                    defaultText = "Поиск по тортам",
                    backgroundColor = colorResource(R.color.dark_background),
                    onChange = onSearchTextChange,
                    onSearch = onSearch
                )
                SortSelector(
                    currentSort = state.currentSorting,
                    options = Sorting.entries,
                    onSortSelected = onSortSelected
                )
                Spacer(Modifier.height(30.dp))
                LazyVerticalGrid(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    columns = GridCells.Fixed(2),
                    userScrollEnabled = true
                ) {
                    items(state.feed) { item ->
                        CakeFeedItem(
                            name = item.name,
                            weight = item.weight,
                            preparation = item.preparation,
                            price = item.price,
                            onBuy = { onBuy(item) },
                            onOpen = { onOpen(item) },
                            image = null //TODO: add image
                        )
                    }

                }
                if (isLoading) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewCakesFeedStatelessScreen() {
    CakesFeedStatelessScreen(
        topBar = { TopNameBar("saldkfj") { } },
        bottomBar = { BottomNavBar({}, {}, {}, {}, Screen.MAIN) },
        state = CakesFeedState(
            feed = arrayListOf(
                CakeGeneral(
                    1,
                    name = "TODO()",
                    price = 1000,
                    description = "sdfhoij",
                    diameter = 10f,
                    weight = 1f,
                    preparation = 3,
                    confectioner = Confectioner(
                        id = 1,
                        name = "TODO()",
                        phoneNumber = "TODO()",
                        email = "TODO()",
                        description = "TODO()",
                        address = "TODO()"
                    ),
                )
            )
        ),
        onOpen = {},
        onSearchTextChange = {},
        onSearch = {},
        isLoading = true,
        onLoadMore = {},
        onBuy = {},
        onSortSelected = {},
    )
}