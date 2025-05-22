package dev.tp_94.mobileapp.custom_order_settings.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun SectionHeader(text: String) {
    Text(
        text = text,
        style = TextStyles.secondHeader(colorResource(R.color.dark_text)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}
