package dev.tp_94.mobileapp.cakes_feed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.BlockButton
import dev.tp_94.mobileapp.core.themes.BuyButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun CakeFeedItem(
    modifier: Modifier = Modifier,
    name: String,
    weight: Float,
    preparation: Int,
    price: Int,
    onBuyEnabled: Boolean = true,
    onBuy: () -> Unit,
    onOpen: () -> Unit,
    image: Painter? = null
) {
    BlockButton(
        onClick = onOpen,
        modifier = modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .graphicsLayer {
                shadowElevation = 4f
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp, 8.dp),
        ) {
            if (image == null) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .background(
                            color = colorResource(R.color.dark_background),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.nocake),
                        contentDescription = null,
                        tint = colorResource(R.color.light_text),
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            image?.let {
                Image(
                    painter = it,
                    contentDescription = "Торт",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1f)
                        .clip(RoundedCornerShape(8.dp))
                        .padding(bottom = 5.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = name,
                style = TextStyles.secondHeader(colorResource(R.color.dark_text))
            )
            Text(
                text = "$weight кг/ от $preparation " + (if (preparation == 1) "дня" else "дней"),
                style = TextStyles.regular(colorResource(R.color.middle_text))
            )
            Spacer(Modifier.height(12.dp))
            BuyButton(
                onClick = onBuy,
                modifier = Modifier
                    .padding(bottom = 7.dp)
                    .fillMaxWidth()
                    .align(Alignment.Start),
                shape = RoundedCornerShape(12.dp),
                enabled = onBuyEnabled,
                text = "от $price ₽"
            )
        }
    }
}

@Preview
@Composable
fun PreviewItem() {
    CakeFeedItem(
        name = "TODO()",
        weight = 1f,
        preparation = 1,
        price = 1,
        onBuy = { },
        onOpen = { }
    )
}

@Composable
fun CakeFeedItemEditable(
    name: String,
    weight: Float,
    preparation: Int,
    price: Int,
    onOpen: () -> Unit,
    image: Painter? = null
) {
    BlockButton(
        onClick = onOpen,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(0.dp, 0.dp, 0.dp, 16.dp)
            .graphicsLayer {
                shadowElevation = 4f
                shape = RoundedCornerShape(8.dp)
                clip = true
            }
            .clip(RoundedCornerShape(8.dp)),
        shape = RoundedCornerShape(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .padding(4.dp, 8.dp),
        ) {
            if (image == null) {
                Box(
                    modifier = Modifier
                        .padding(bottom = 5.dp)
                        .background(
                            color = colorResource(R.color.dark_background),
                            shape = RoundedCornerShape(8.dp)
                        )
                        .fillMaxWidth()
                        .aspectRatio(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.nocake),
                        contentDescription = null,
                        tint = colorResource(R.color.light_text),
                        modifier = Modifier.size(50.dp)
                    )
                }
            }
            image?.let {
                Image(
                    painter = it,
                    contentDescription = "Торт",
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .padding(bottom = 5.dp),
                    contentScale = ContentScale.Crop
                )
            }
            Text(
                text = name,
                style = TextStyles.secondHeader(colorResource(R.color.dark_text))
            )
            Text(
                text = "$weight кг/ от $preparation " + (if (preparation == 1) "дня" else "дней"),
                style = TextStyles.regular(colorResource(R.color.middle_text))
            )
            Spacer(Modifier.height(12.dp))
            Box(modifier = Modifier.fillMaxWidth().size(48.dp), contentAlignment = Alignment.CenterEnd) {
                Icon(
                    painter = painterResource(R.drawable.edit_pencil),
                    contentDescription = null,
                    tint = colorResource(R.color.light_text),
                    modifier = Modifier.size(36.dp)
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewEditableItem() {
    CakeFeedItemEditable(
        name = "TODO()",
        weight = 1f,
        preparation = 1,
        price = 1,
        onOpen = { }
    )
}