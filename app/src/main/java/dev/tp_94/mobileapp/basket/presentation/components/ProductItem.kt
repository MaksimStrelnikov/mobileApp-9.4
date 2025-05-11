package dev.tp_94.mobileapp.basket.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun ProductItem(
    cake: CakeGeneral,
    image: Painter? = null,
    amount: Int,
    onChangeAmount: (Int) -> Unit,
) {
    Box(
        modifier = Modifier
            .background(
                color = colorResource(R.color.light_background), shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(140.dp)
        ) {
            if (image == null) {
                Box(
                    modifier = Modifier
                        .size(140.dp)
                        .background(
                            color = colorResource(R.color.dark_background),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.nocake),
                        contentDescription = null,
                        tint = colorResource(R.color.light_text),
                        modifier = Modifier.size(50.dp)
                    )
                }
            } else {
                Image(
                    painter = painterResource(R.drawable.mock_cake),
                    contentDescription = null,
                    modifier = Modifier.size(140.dp)
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(5.dp)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.SpaceAround,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    text = cake.name,
                    style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                )
                Text(
                    text = cake.confectioner.address,
                    style = TextStyles.regular(colorResource(R.color.dark_text))
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${cake.price} ₽",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                    )
                    Text(
                        text = "${cake.weight} кг/ от ${cake.preparation} " +
                                if (cake.preparation == 1) "дня" else "дней",
                        style = TextStyles.regular(colorResource(R.color.light_text))
                    )
                }
                Row(
                    modifier = Modifier
                        .wrapContentWidth()
                        .align(Alignment.CenterHorizontally),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = { onChangeAmount(amount - 1) }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.minusbutton),
                            contentDescription = null
                        )
                    }
                    Text(
                        text = "$amount шт.",
                        style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                    )
                    IconButton(
                        onClick = { onChangeAmount(amount + 1) }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.plusbutton),
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}


@Preview
@Composable
fun PreviewProductItem() {
    ProductItem(
        cake = CakeGeneral(
            price = 1000,
            name = "TODO()",
            description = "TODO()",
            diameter = 3f,
            weight = 3f,
            preparation = 3,
            confectioner = Confectioner(
                id = 1,
                name = "TODO()",
                phoneNumber = "TODO()",
                email = "TODO()",
                description = "TODO()",
                address = "TODO()"
            )
        ),
        image = painterResource(R.drawable.mock_cake),
        amount = 3,
        onChangeAmount = { }
    )
}