package dev.tp_94.mobileapp.basket.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun TotalSum(
    modifier: Modifier = Modifier,
    sum: Int
) {
    Column(
        modifier = modifier
    ) {
        Text(
            text = "Итого",
            style = TextStyles.header(color = colorResource(R.color.middle_text)),
        )
        Text(
            text = "$sum ₽",
            style = TextStyles.header(color = colorResource(R.color.dark_text), fontSize = 32.sp),
        )
    }
}