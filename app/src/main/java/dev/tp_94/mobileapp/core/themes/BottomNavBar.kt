package dev.tp_94.mobileapp.core.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R

@Composable
fun BottomNavBar(
    onMainClick: () -> Unit,
    onOrdersClick: () -> Unit,
    onBusketClick: () -> Unit,
    onProfileClick: () -> Unit,
    currentScreen: Screen
) {
    val size = 70.dp
    Box(
        modifier = Modifier
            .background(
                color = colorResource(R.color.light_background)
            )
            .fillMaxWidth()
            .wrapContentHeight(),
        contentAlignment = Alignment.Center
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(
                onClick = onMainClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size)
                    .weight(0.33f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.main_icon),
                    contentDescription = "Главная",
                    tint = if (currentScreen == Screen.MAIN) colorResource(R.color.dark_accent) else colorResource(
                        R.color.dark_text
                    )
                )
            }
            IconButton(
                onClick = onOrdersClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size)
                    .weight(0.33f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.orders_icon),
                    contentDescription = "Заказы",
                    tint = if (currentScreen == Screen.ORDERS) colorResource(R.color.dark_accent) else colorResource(
                        R.color.dark_text
                    )
                )
            }
            IconButton(
                    onClick = onMainClick,
            modifier = Modifier
                .fillMaxWidth()
                .height(size)
                .weight(0.33f)
            ) {
            Icon(
                painter = painterResource(R.drawable.basket_icon),
                contentDescription = "Корзина",
                tint = if (currentScreen == Screen.BUSKET) colorResource(R.color.dark_accent) else colorResource(
                    R.color.dark_text
                )
            )
        }
            IconButton(
                onClick = onProfileClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(size)
                    .weight(0.33f)
            ) {
                Icon(
                    painter = painterResource(R.drawable.profile_icon),
                    contentDescription = "Профиль",
                    tint = if (currentScreen == Screen.PROFILE) colorResource(R.color.dark_accent) else colorResource(
                        R.color.dark_text
                    )
                )
            }
        }
    }
}

enum class Screen {
    MAIN,
    ORDERS,
    PROFILE,
    BUSKET
}

@Preview
@Composable
fun PreviewBottomNavBar() {
    BottomNavBar(
        onMainClick = {   },
        onOrdersClick = {   },
        onBusketClick = {   },
        onProfileClick = {   },
        currentScreen = Screen.MAIN
    )
}