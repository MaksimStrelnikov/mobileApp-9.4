package dev.tp_94.mobileapp.self_made_cake.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun FillingAddUneditable(text: String) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .wrapContentWidth()
            .background(
                color = colorResource(R.color.light_background), shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = colorResource(R.color.light_text),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp), contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = TextStyles.regular(colorResource(R.color.dark_text)),
                modifier = Modifier
                    .padding(horizontal = 10.dp),
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}


@Preview
@Composable
fun PreviewUneditable() {
    FillingAddUneditable(
        "szfgzfdgdfasg"
    )
}

@Composable
fun FillingAddEditable(text: String, onDelete: () -> Unit) {
    Box(
        modifier = Modifier
            .height(48.dp)
            .width(IntrinsicSize.Min)
            .background(
                color = colorResource(R.color.light_background), shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = colorResource(R.color.light_text),
                shape = RoundedCornerShape(8.dp)
            )
            .padding(horizontal = 8.dp), contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.wrapContentWidth(), verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = text,
                style = TextStyles.regular(colorResource(R.color.dark_text)),
                modifier = Modifier
                    .padding(start = 10.dp, bottom = 3.dp)
                    .weight(weight = 1f, fill = false),
                softWrap = false,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            IconButton(
                onClick = onDelete
            ) {
                Icon(
                    painter = painterResource(R.drawable.delete),
                    contentDescription = null,
                    tint = colorResource(R.color.dark_text),
                    modifier = Modifier.padding(start = 18.dp, end = 10.dp)
                )
            }
        }
    }
}


@Preview
@Composable
fun PreviewEditable() {
    FillingAddEditable(
        "sфварыпаврверфыкрв",
    ) {}
}

@Composable
fun FillingNew(onClick: () -> Unit, modifier: Modifier = Modifier) {
    IconButton(
        onClick = onClick,
        modifier = modifier
            .size(48.dp)
            .background(
                color = colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
            .border(
                width = 2.dp,
                color = colorResource(R.color.light_text),
                shape = RoundedCornerShape(8.dp)
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = null,
            tint = colorResource(R.color.dark_text)
        )
    }
}

@Preview
@Composable
fun PreviewNew() {
    FillingNew(
        {}
    )
}