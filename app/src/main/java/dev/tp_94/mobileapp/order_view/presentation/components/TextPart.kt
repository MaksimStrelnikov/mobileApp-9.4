package dev.tp_94.mobileapp.order_view.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun TextPartWithPairs(header: String, list: List<Pair<String, String>>) {
    Text(
        text = header,
        style = TextStyles.regular(
            colorResource(R.color.dark_text),
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(bottom = 11.dp)
    )
    list.forEach{
        Row(
            modifier = Modifier.padding(bottom = 11.dp)
        ) {
            Text(
                text = it.first,
                style = TextStyles.regular(colorResource(R.color.middle_text)),
                modifier = Modifier.weight(1f)
            )
            Text(
                text = it.second,
                style = TextStyles.regular(colorResource(R.color.middle_text))
            )
        }
    }
}

@Composable
fun TextPartWithPairs(header: String, pair: Pair<String, String>) {
    TextPartWithPairs(header, listOf(pair))
}

@Composable
fun TextPart(header: String, list: List<String>) {
    Text(
        text = header,
        style = TextStyles.regular(
            colorResource(R.color.dark_text),
            fontWeight = FontWeight.SemiBold
        ),
        modifier = Modifier.padding(bottom = 11.dp)
    )
    list.forEach{
        Row(
            modifier = Modifier.padding(bottom = 11.dp)
        ) {
            Text(
                text = it,
                style = TextStyles.regular(colorResource(R.color.middle_text)),
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun TextPart(header: String, text: String) {
    TextPart(header, listOf(text))
}