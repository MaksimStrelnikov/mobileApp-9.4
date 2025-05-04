package dev.tp_94.mobileapp.mainconfectioner.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import dev.tp_94.mobileapp.confectionerpage.presentation.components.ConfectionerCard
import dev.tp_94.mobileapp.core.models.Confectioner

@Composable
fun MainConfectionerStatefulScreen(
    viewModel: MainConfectionerViewModel = hiltViewModel(),
    onMyProfileClick: () -> Unit,
    onCustomOrdersClick: () -> Unit,
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
    )
}

@Composable
fun MainConfectionerStatelessScreen(
    state: MainConfectionerState,
    onMyProfileClick: () -> Unit,
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
            products = arrayListOf()
        ),
        onMyProfileClick = { TODO() },
        onCustomOrdersClick = { TODO() }
    )
}
