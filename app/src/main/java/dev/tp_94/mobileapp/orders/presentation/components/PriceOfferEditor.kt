package dev.tp_94.mobileapp.orders.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.addproduct.presentation.components.PriceEditor
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun PriceOfferEditor(onDismiss: () -> Unit, onClick: () -> Unit) {
    val price = remember { mutableStateOf("") }
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .padding(15.dp)
                .background(
                    color = colorResource(R.color.background),
                    shape = RoundedCornerShape(8.dp)
                )
                .border(
                    width = 2.dp,
                    color = colorResource(R.color.light_text),
                    shape = RoundedCornerShape(8.dp)
                )
        ) {
            Column(
                modifier = Modifier.padding(17.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Назначить цену",
                    style = TextStyles.header(colorResource(R.color.dark_text))
                )
                Spacer(Modifier.height(12.dp))
                PriceEditor(
                    text = price.value,
                    onChange = { price.value = it },
                    defaultText = "Цена в рублях",
                    backgroundColor = colorResource(R.color.dark_background),
                )
                Spacer(Modifier.height(12.dp))
                ActiveButton(
                    onClick = onClick,
                    modifier = Modifier
                        .height(48.dp)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text(
                        text = "Предложить",
                        style = TextStyles.button(colorResource(R.color.light_background))
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewPriceEditor() {
    PriceOfferEditor(
        onDismiss = {}
    ) {

    }
}