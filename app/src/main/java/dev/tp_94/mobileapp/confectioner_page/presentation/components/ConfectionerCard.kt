package dev.tp_94.mobileapp.confectioner_page.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.Restrictions
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun ConfectionerCard(
    name: String,
    address: String,
    description: String,
    restrictions: Restrictions? = null,
    onMyProfileClick: (() -> Unit)? = null,
    onButtonClick: () -> Unit,
    customOrdersText: String = "Индивидуальный заказ"
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .background(
                color = colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .background(
                            color = colorResource(R.color.dark_background),
                            shape = RoundedCornerShape(100)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(R.drawable.account),
                        contentDescription = null,
                        tint = colorResource(R.color.dark_text)
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(8.dp, 8.dp, 8.dp, 0.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = name,
                        style = TextStyles.header(colorResource(R.color.dark_text)),
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                    )
                    if (onMyProfileClick != null) {
                        DiscardButton(
                            onMyProfileClick,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "Мой профиль",
                                style = TextStyles.button(colorResource(R.color.dark_text))
                            )
                        }
                    }
                }
            }
            Text(
                text = address,
                style = TextStyles.secondHeader(colorResource(R.color.dark_text)),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            Text(
                text = description,
                style = TextStyles.regular(colorResource(R.color.dark_text)),
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            if (restrictions!= null && restrictions.isCustomAcceptable) {
                ActiveButton(
                    onClick = onButtonClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                ) {
                    Text(
                        text = customOrdersText,
                        style = TextStyles.button(colorResource(R.color.light_background))
                    )
                }
                if (restrictions.isShapeAcceptable) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Можно сгенерировать свою идею",
                        style = TextStyles.regular(colorResource(R.color.light_text)),
                    )
                }
                if (restrictions.isImageAcceptable) {
                    Spacer(Modifier.height(4.dp))
                    Text(
                        text = "Работает с фото",
                        style = TextStyles.regular(colorResource(R.color.light_text)),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewConfectionerCard() {
    ConfectionerCard(
        name = "Веснушка",
        address = "asdflkgjhljkfsdhnljk;",
        description = "IHBKIJGRHUIGNTRLUOINTGHOINLHTINLIKFSGgfhlnjikftrdbuyioetruoibUOIHGUOINRInFHTJKnhtfoilk",
        onMyProfileClick = {},
        onButtonClick = {},
        restrictions = Restrictions(true, true, true)
    )
}

@Preview
@Composable
fun PreviewConfectionerCard2() {
    ConfectionerCard(
        name = "Веснушка",
        address = "asdflkgjhljkfsdhnljk;",
        description = "",
        onMyProfileClick = {},
        onButtonClick = {},
    )
}