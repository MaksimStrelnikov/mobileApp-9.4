package dev.tp_94.mobileapp.selfmadecake.presentation.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.Fonts
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun TextEditor(onChange: (String) -> Unit, text: String, header: String) {
    Box(
        modifier = Modifier
            .background(
                colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
            .width(360.dp)
            .wrapContentHeight()
            .padding(19.dp, 17.dp)
    ) {
        Column {
            Text(
                header,
                fontFamily = Fonts.robotoSlab,
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 20.sp,
                letterSpacing = 0.sp,
                color = colorResource(R.color.dark_text),
                modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 16.dp)
            )
            BasicTextField(
                text,
                onChange,
                enabled = true,
                modifier = Modifier
                    .background(
                        colorResource(R.color.background),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .border(
                        BorderStroke(
                            color = colorResource(R.color.dark_background),
                            width = 4.dp
                        ),
                        shape = RoundedCornerShape(8.dp)
                    )
                    .padding(9.dp, 8.dp)
                    .fillMaxWidth()
                    .height(70.dp),
                textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
                decorationBox = { innerTextField ->
                    Box(
                        Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                    ) {
                        if (text.isEmpty()) {
                            Text(
                                "Введите текст...",
                                style = TextStyles.regular(colorResource(R.color.light_text))
                            )
                        }
                        innerTextField()
                    }
                }
            )
        }
    }
}

@Composable
fun InteractableText(
    text: String,
    textOffset: Offset,
    textStyle: TextStyle,
    onOffsetChanged: (Offset) -> Unit
) {
    val currentOffset by rememberUpdatedState(textOffset)

    Text(
        text = text,
        style = textStyle,
        modifier = Modifier
            .offset { IntOffset(currentOffset.x.toInt(), currentOffset.y.toInt()) }
            .pointerInput(Unit) {
                detectDragGestures { change, dragAmount ->
                    change.consume()
                    onOffsetChanged(currentOffset + dragAmount)
                }
            }
    )
}