package dev.tp_94.mobileapp.payment_result

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun ErrorPayment(
    onDismissRequest: () -> Unit,
    mainText: String = "Оплата не прошла",
    description: String = "Пожалуйста, смените способ оплаты\nили попробуйте позднее"
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = colorResource(R.color.background)
            )
            .padding(24.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.error),
                contentDescription = "Success",
            )
            Text(
                text = mainText,
                style = TextStyles.header(colorResource(R.color.dark_text)),
                modifier = Modifier
                    .padding(top = 24.dp)
            )
            Text(
                text = description,
                style = TextStyles.regular(colorResource(R.color.light_text)),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 24.dp)
            )
            ActiveButton(
                onClick = onDismissRequest,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 60.dp)
            ) {
                Text(
                    text = "Вернуться в приложение",
                    style = TextStyles.button(colorResource(R.color.light_background))
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewErrorPayment() {
    ErrorPayment(onDismissRequest = {})
}
