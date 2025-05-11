package dev.tp_94.mobileapp.order_view.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun ConfectionerBubble(
    image: Painter? = null,
    name: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .padding(vertical = 20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = colorResource(R.color.light_background),
                    shape = RoundedCornerShape(8.dp)
                )
                .clickable(onClick = onClick),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(13.dp, 10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (image == null) {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .background(
                                color = colorResource(R.color.dark_background),
                                shape = RoundedCornerShape(100)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.account),
                            contentDescription = null,
                            tint = colorResource(R.color.middle_text)
                        )
                    }
                } else {
                    Image(
                        painter = image,
                        contentDescription = null,
                        modifier = Modifier
                            .clip(RoundedCornerShape(100))
                            .size(64.dp)
                    )
                }
                Spacer(Modifier.width(15.dp))
                Text(
                    text = name,
                    style = TextStyles.header(color = colorResource(R.color.dark_text))
                )
            }
        }
    }
}