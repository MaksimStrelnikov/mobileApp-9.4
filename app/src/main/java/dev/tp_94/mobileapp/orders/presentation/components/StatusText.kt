package dev.tp_94.mobileapp.orders.presentation.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun StatusText(text: String) {
    Text(
        text = text,
        style = TextStyles.secondHeader(colorResource(R.color.dark_accent)),
        modifier = Modifier.padding(top = 15.dp)
    )
}