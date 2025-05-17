package dev.tp_94.mobileapp.order_payment.presentation.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun CardsList(
    cardList: List<Card>,
    selected: Card?,
    onSelect: (Card?) -> Unit
) {
    Log.println(Log.INFO, "Log", "Recomposition $selected")
    Column {
        cardList.forEach {
            CardListItem(
                cardNumber = it.number,
                isSelected = it == selected,
                onSelect = { onSelect(it) },
            )
            Spacer(Modifier.height(2.dp))
        }
        CardListItem(
            isSelected = selected == null,
            onSelect = { onSelect(null) },
        )
    }
}

@Preview
@Composable
fun PreviewCardsList() {
    CardsList(
        cardList = arrayListOf(
            Card(
                number = "1234567890123456",
                expirationDate = "22/22",
                cvcCode = "123"
            )
        ),
        selected = null,
        onSelect = { },
    )
}

@Composable
fun CardListItem(
    cardNumber: String? = null,
    isSelected: Boolean,
    onSelect: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = if (isSelected) colorResource(R.color.accent) else Color.Transparent,
                shape = RoundedCornerShape(8.dp)
            )
            .clickable(onClick = onSelect)
            .padding(horizontal = 8.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = if (cardNumber == null) {
                "Добавить новую карту"
            } else {
                "**" + cardNumber.drop(12)
            },
            style = TextStyles.regular(colorResource(R.color.dark_text)),
            modifier = Modifier
                .weight(1f)
                .padding(start = 6.dp)
        )
        RadioButton(
            selected = isSelected,
            onClick = null,
            colors = RadioButtonColors(
                selectedColor = colorResource(R.color.middle_text),
                unselectedColor = colorResource(R.color.middle_text),
                disabledSelectedColor = colorResource(R.color.middle_text),
                disabledUnselectedColor = colorResource(R.color.middle_text)
            )
        )
    }
}