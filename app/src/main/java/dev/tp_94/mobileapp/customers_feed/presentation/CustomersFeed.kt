package dev.tp_94.mobileapp.customers_feed.presentation

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.CircularProgressIndicator
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
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.customers_feed.presentation.components.ConfectionerFeedItem
import dev.tp_94.mobileapp.customers_feed.presentation.components.SearchInput
import dev.tp_94.mobileapp.customers_feed.presentation.components.SortSelector

@Composable
fun CustomersFeedStatefulScreen(
    viewModel: CustomersFeedViewModel = hiltViewModel(),
    onNavigateToConfectioner: (Confectioner) -> Unit,
    onBackClick: () -> Unit,
    onError: () -> Unit
) {
    val user = viewModel.getUser()
    LaunchedEffect(user) {
        if (user == null || user !is Customer) {
            onError()
            viewModel.exit()
        }
    }
    val state by viewModel.state.collectAsStateWithLifecycle()
    CustomersFeedStatelessScreen(
        state = state,
        onSearchTextChange = { viewModel.updateSearchText(it) },
        onSearch = { viewModel.search() },
        onNavigateToConfectioner = onNavigateToConfectioner,
        onLoadMore = { viewModel.loadMore() },
        topBar = {
            TopNameBar(
                name = "Поиск кондитеров",
                onBackClick = onBackClick
            )
        },
        onSortSelected = { viewModel.selectSort(it) },
        bottomBar = { },
    )
}

@Composable
fun CustomersFeedStatelessScreen(
    state: CustomersFeedState,
    onSearchTextChange: (String) -> Unit,
    onSearch: () -> Unit,
    onNavigateToConfectioner: (Confectioner) -> Unit,
    onSortSelected: (Sorting) -> Unit,
    onLoadMore: () -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit,
) {
    val listState = rememberLazyListState()
    /* TODO: fix endless searching
    LaunchedEffect(listState) {
    snapshotFlow { listState.layoutInfo.visibleItemsInfo }
        .collect { visibleItems ->
            val lastVisibleItemIndex = visibleItems.lastOrNull()?.index ?: return@collect
            if (lastVisibleItemIndex >= state.feed.size && !state.isLoading) {
                onLoadMore()
            }
        }
    }*/

    LaunchedEffect(state.isLoading) {
        Log.println(Log.INFO, "Log", "isLoading changed: ${state.isLoading}")
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
                    defaultText = "Поиск по кондитерам",
                    backgroundColor = colorResource(R.color.dark_background),
                    onChange = onSearchTextChange,
                    onSearch = onSearch
                )
                SortSelector(
                    currentSort = state.currentSorting,
                    options = Sorting.entries,
                    onSortSelected = onSortSelected,
                )
                Spacer(Modifier.height(30.dp))
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    userScrollEnabled = true
                ) {
                    items(state.feed) { item ->
                        ConfectionerFeedItem(
                            name = item.name,
                            onClick = { onNavigateToConfectioner(item) },
                        )
                    }
                    if (state.isLoading) {
                        item {
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularProgressIndicator(color = colorResource(R.color.light_text))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCustomersFeedStatelessScreen() {
    CustomersFeedStatelessScreen(
        topBar = { TopNameBar("saldkfj") { } },
        bottomBar = { BottomNavBar({}, {}, {}, {}, Screen.MAIN) },
        state = CustomersFeedState(
            feed = arrayListOf(
                Confectioner(
                    id = 1,
                    name = "Кондитер",
                    phoneNumber = "",
                    email = "",
                    description = "",
                    address = ""
                )
            ),
            searchText = ""
        ),
        onNavigateToConfectioner = {},
        onSearchTextChange = {},
        onSearch = {},
        onLoadMore = {},
        onSortSelected = { },
    )
}