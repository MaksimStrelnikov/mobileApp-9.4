package dev.tp_94.mobileapp.core.themes

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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

@Composable
fun TopNameBar(name: String, onBackClick: (() -> Unit)?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = colorResource(R.color.background))
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp, 8.dp)
        ) {
            Text(
                text = name,
                style = TextStyles.header(color = colorResource(R.color.dark_text)),
                modifier = Modifier
                    .wrapContentWidth(),
                textAlign = TextAlign.Center
            )
            if (onBackClick != null) {
                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.back),
                        contentDescription = "Назад",
                        tint = colorResource(R.color.dark_text)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewTopNameBar() {
    TopNameBar(
        name = "Экран",
        onBackClick = { }
    )
}