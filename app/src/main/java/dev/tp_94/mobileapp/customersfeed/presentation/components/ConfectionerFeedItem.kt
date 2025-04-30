package dev.tp_94.mobileapp.customersfeed.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.BlockButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun ConfectionerFeedItem(
    name: String,
    avatar: Painter? = null,
    imageFirst: Painter? = painterResource(R.drawable.mock_cake),
    imageSecond: Painter? = painterResource(R.drawable.mock_cake2),
    onClick: () -> Unit
) {
    BlockButton(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(0.dp, 0.dp, 0.dp, 30.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(13.dp, 15.dp, 13.dp, 0.dp)
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (avatar == null) {
                    Image(
                        painter = painterResource(R.drawable.account),
                        contentDescription = "Аватара нет",
                        modifier = Modifier
                            .size(64.dp)
                            .clip(RoundedCornerShape(100))
                            .background(color = colorResource(R.color.dark_background)),
                        contentScale = ContentScale.Inside
                    )
                } else {
                    Image(
                        painter = avatar,
                        contentDescription = "Аватар",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                Spacer(Modifier.width(12.dp))
                Text(
                    text = name,
                    style = TextStyles.secondHeader(colorResource(R.color.dark_text))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp, 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                imageFirst?.let {
                    Image(
                        painter = it,
                        contentDescription = "Торт",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
                imageSecond?.let {
                    Image(
                        painter = it,
                        contentDescription = "Торт",
                        modifier = Modifier
                            .size(150.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        }
    }
}