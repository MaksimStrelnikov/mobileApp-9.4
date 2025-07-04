package dev.tp_94.mobileapp.profile.presentation

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.themes.BlockButton
import dev.tp_94.mobileapp.core.themes.BottomNavBar
import dev.tp_94.mobileapp.core.themes.Screen
import dev.tp_94.mobileapp.core.themes.TextButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar

@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
    confectionerRoutes: ProfileConfectionerRoutes,
    customerRoutes: ProfileCustomerRoutes,
    onError: () -> Unit,
    onLogout: () -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    val user = viewModel.session.collectAsStateWithLifecycle().value?.user
    Log.println(Log.INFO, "Log", user.toString())

    LaunchedEffect(user) {
        if (user == null) {
            onError()
        }
    }
    when (user) {
        is Confectioner -> ProfileConfectionerStatelessScreen(
            name = user.name,
            phoneNumber = user.phoneNumber,
            onWithdraw = confectionerRoutes.onWithdraw,
            onChangePersonalData = confectionerRoutes.onChangePersonalData,
            onViewOrders = confectionerRoutes.onViewOrders,
            onChangeCustomCake = confectionerRoutes.onChangeCustomCake,
            onLogout = {
                viewModel.logout()
                onLogout()
            },
            onDeleteAccount = {
                viewModel.deleteAccount(onLogout)
            },
            onAddCake = confectionerRoutes.onAddCake,
            topBar = topBar
        )

        is Customer -> ProfileCustomerStatelessScreen(
            name = user.name,
            phoneNumber = user.phoneNumber,
            onChangePersonalData = customerRoutes.onChangePersonalData,
            onViewOrders = customerRoutes.onViewOrders,
            onLogout = {
                viewModel.logout()
                onLogout()
            },
            onDeleteAccount = {
                viewModel.deleteAccount(
                    onLogout = onLogout
                )
            },
            topBar = topBar,
            bottomBar = bottomBar,
        )

        null -> {
            Surface(
                {},
                Modifier
                    .fillMaxSize(),
                color = colorResource(R.color.background)
            ) { }
        }
    }
}

@Composable
fun ProfileCustomerStatelessScreen(
    name: String,
    phoneNumber: String,
    onChangePersonalData: () -> Unit,
    onViewOrders: () -> Unit,
    onLogout: () -> Unit,
    onDeleteAccount: () -> Unit,
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Log.println(Log.INFO, "Log", "Rebuild")
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(color = colorResource(R.color.background))
                .fillMaxSize()
                .padding(0.dp, 12.dp)
        ) {
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(38.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = name,
                            style = TextStyles.secondHeader(
                                color = colorResource(R.color.middle_text)
                            )
                        )
                        Text(
                            text = "+7$phoneNumber",
                            style = TextStyles.regular(
                                color = colorResource(R.color.light_text)
                            )
                        )
                    }
                    IconButton(
                        onClick = onChangePersonalData
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.forward),
                            contentDescription = "Дальше",
                            tint = colorResource(R.color.dark_text)
                        )
                    }
                }
            }
            BlockButton(
                onClick = onViewOrders,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Мои заказы",
                        modifier = Modifier
                            .weight(1f),
                        style = TextStyles.regular(color = colorResource(R.color.dark_text))
                    )
                    Icon(
                        painter = painterResource(R.drawable.forward),
                        contentDescription = "Дальше",
                        tint = colorResource(R.color.dark_text)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally)) {
                TextButton(
                    onClick = onLogout,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Выйти из аккаунта",
                        style = TextStyles.button(color = colorResource(R.color.dark_accent))
                    )
                }
                TextButton(
                    onClick = onDeleteAccount,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        "Удалить аккаунт",
                        style = TextStyles.button(color = colorResource(R.color.light_text))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewProfileCustomerStatelessScreen() {
    ProfileCustomerStatelessScreen(
        name = "Вася Пупкин",
        phoneNumber = "9042865641",
        onChangePersonalData = { },
        onViewOrders = { },
        onLogout = { },
        onDeleteAccount = { },
        topBar = {
            TopNameBar(
                name = "Профиль"
            ) { }
        },
        bottomBar = {
            BottomNavBar(
                onMainClick = { },
                onOrdersClick = { },
                onProfileClick = { },
                onBasketClick = { },
                currentScreen = Screen.PROFILE
            )
        },
    )
}

@Composable
fun ProfileConfectionerStatelessScreen(
    name: String,
    phoneNumber: String,
    onWithdraw: () -> Unit,
    onChangePersonalData: () -> Unit,
    onViewOrders: () -> Unit,
    onChangeCustomCake: () -> Unit,
    onDeleteAccount: () -> Unit,
    onLogout: () -> Unit,
    onAddCake: () -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .background(color = colorResource(R.color.background))
                .fillMaxSize()
        ) {
            Box {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(38.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                    ) {
                        Text(
                            text = name,
                            style = TextStyles.secondHeader(
                                color = colorResource(R.color.middle_text)
                            )
                        )
                        Text(
                            text = "+7$phoneNumber",
                            style = TextStyles.regular(
                                color = colorResource(R.color.light_text)
                            )
                        )
                    }
                    IconButton(
                        onClick = onChangePersonalData
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.forward),
                            contentDescription = "Дальше",
                            tint = colorResource(R.color.dark_text)
                        )
                    }
                }
            }
            BlockButton(
                onClick = onWithdraw,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Мои финансы",
                        modifier = Modifier
                            .weight(1f),
                        style = TextStyles.regular(color = colorResource(R.color.dark_text))
                    )
                    Icon(
                        painter = painterResource(R.drawable.forward),
                        contentDescription = "Дальше",
                        tint = colorResource(R.color.dark_text)
                    )
                }
            }
            BlockButton(
                onClick = onViewOrders,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp,),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Мои заказы",
                        modifier = Modifier
                            .weight(1f),
                        style = TextStyles.regular(color = colorResource(R.color.dark_text))
                    )
                    Icon(
                        painter = painterResource(R.drawable.forward),
                        contentDescription = "Дальше",
                        tint = colorResource(R.color.dark_text)
                    )
                }
            }
            Spacer(Modifier.height(16.dp))
            BlockButton(
                onClick = onChangeCustomCake,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp),
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Настройка индивидуальных заказов",
                        modifier = Modifier
                            .weight(1f),
                        style = TextStyles.regular(color = colorResource(R.color.dark_text))
                    )
                    Icon(
                        painter = painterResource(R.drawable.forward),
                        contentDescription = "Дальше",
                        tint = colorResource(R.color.dark_text)
                    )
                }
            }
            BlockButton(
                onClick = onAddCake,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                shape = RoundedCornerShape(8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(8.dp, 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Добавить товар",
                        modifier = Modifier
                            .weight(1f),
                        style = TextStyles.regular(color = colorResource(R.color.dark_text))
                    )
                    Icon(
                        painter = painterResource(R.drawable.forward),
                        contentDescription = "Дальше",
                        tint = colorResource(R.color.dark_text)
                    )
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Column(modifier = Modifier.fillMaxWidth()
                .align(Alignment.CenterHorizontally)) {
                TextButton(
                    onClick = onLogout,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        "Выйти из аккаунта",
                        style = TextStyles.button(color = colorResource(R.color.dark_accent))
                    )
                }
                TextButton(
                    onClick = onDeleteAccount,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                        .padding(bottom = 8.dp)
                ) {
                    Text(
                        "Удалить аккаунт",
                        style = TextStyles.button(color = colorResource(R.color.light_text))
                    )
                }
            }

        }

    }
}


@Preview
@Composable
fun PreviewProfileConfectionerStatelessScreen() {
    ProfileConfectionerStatelessScreen(
        name = "Вася Пупкин",
        phoneNumber = "9042865641",
        onChangePersonalData = { },
        onViewOrders = { },
        onChangeCustomCake = { },
        onLogout = { },
        onDeleteAccount = { },
        onAddCake = { },
        topBar = {
            TopNameBar(
                name = "Профиль"
            ) { }
        },
        onWithdraw = {}
    )
}