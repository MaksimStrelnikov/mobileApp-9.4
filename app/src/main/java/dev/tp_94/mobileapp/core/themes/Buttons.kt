package dev.tp_94.mobileapp.core.themes


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import dev.tp_94.mobileapp.R

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateValue
import androidx.compose.foundation.layout.offset
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import kotlinx.coroutines.delay

@Composable
fun ActiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit = {}
) {
    val buttonColors = ButtonColors(
        colorResource(id = R.color.dark_accent),
        colorResource(id = R.color.light_background),
        colorResource(id = R.color.dark_background),
        colorResource(id = R.color.middle_text)
    )

    Button(
        onClick,
        modifier,
        enabled,
        shape,
        buttonColors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) { content() }
}

@Composable
fun DiscardButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    content: @Composable () -> Unit = {}
) {
    val discardButtonColors = ButtonColors(
        colorResource(id = R.color.dark_background),
        colorResource(id = R.color.middle_text),
        colorResource(id = R.color.dark_background),
        colorResource(id = R.color.middle_text)
    )

    Button(
        onClick,
        modifier,
        enabled,
        shape,
        discardButtonColors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) { content() }
}

@Composable
fun TextButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    textColor: Color = colorResource(id = R.color.light_background),
    content: @Composable () -> Unit = {}
) {
    val buttonColors = ButtonColors(
        containerColor = Color.Transparent,
        contentColor = textColor,
        disabledContainerColor = Color.Transparent,
        disabledContentColor = colorResource(R.color.light_text)
    )

    Button(
        onClick,
        modifier,
        enabled,
        shape,
        buttonColors,
        elevation,
        border,
        contentPadding,
        interactionSource
    ) { content() }
}

@Composable
fun BuyButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = RoundedCornerShape(12.dp),
    elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
    border: BorderStroke? = null,
    contentPadding: PaddingValues = PaddingValues(4.dp),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    backgroundColor: Color = colorResource(id = R.color.dark_background),
    textColor: Color = colorResource(id = R.color.middle_text),
    text: String = "Цена не задана",
) {
    var isClicked by remember { mutableStateOf(false) }
    var showPlusOne by remember { mutableStateOf(false) }

    LaunchedEffect(showPlusOne) {
        if (showPlusOne) {
            delay(300)
            showPlusOne = false
        }
    }

    Box(modifier = modifier) {
        Button(
            onClick = {
                onClick()
                if (!isClicked) {
                    isClicked = true
                }
                showPlusOne = true
            },
            modifier = Modifier,
            enabled = enabled,
            shape = shape,
            colors = ButtonDefaults.buttonColors(
                containerColor = if (isClicked) textColor else backgroundColor,
                contentColor = if (isClicked) backgroundColor else textColor,
                disabledContainerColor = if (isClicked) textColor else backgroundColor,
                disabledContentColor = if (isClicked) backgroundColor else textColor
            ),
            elevation = elevation,
            border = border,
            contentPadding = contentPadding,
            interactionSource = interactionSource
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = if (!isClicked) painterResource(R.drawable.busket) else painterResource(R.drawable.plus),
                    contentDescription = null,
                    tint = if (isClicked) backgroundColor else textColor,
                    modifier = Modifier.padding(start = 8.dp)
                )

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = if (isClicked) "В корзине" else text,
                        color = if (isClicked) backgroundColor else textColor,
                        textAlign = TextAlign.Center,
                        style = TextStyles.button(color = colorResource(R.color.middle_text))
                    )
                }
            }
        }

        if (showPlusOne) {
            val infiniteTransition = rememberInfiniteTransition()
            val alpha by infiniteTransition.animateFloat(
                initialValue = 1f,
                targetValue = 0f,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            val offset by infiniteTransition.animateValue(
                initialValue = 10.dp,
                targetValue = (-30).dp,
                typeConverter = Dp.VectorConverter,
                animationSpec = infiniteRepeatable(
                    animation = tween(3000, easing = LinearEasing),
                    repeatMode = RepeatMode.Reverse
                )
            )

            Text(
                text = "+1",
                color = colorResource(R.color.light_accent),
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .offset(x = (-20).dp, y = offset)
                    .alpha(alpha),
                style = TextStyles.secondHeader(colorResource(R.color.dark_accent))
            )
        }
    }
}

@Preview
@Composable
fun PreviewBuyButton() {
    BuyButton(
        {},
        shape = RoundedCornerShape(12.dp),
        text = "от 1000 р",
    )
}

@Composable
fun BlockButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    padding: PaddingValues = PaddingValues(6.dp),
    shape: Shape = RoundedCornerShape(12.dp),
    contentAlignment: Alignment = Alignment.TopStart,
    containerColor: Color = colorResource(id = R.color.light_background),
    content: @Composable () -> Unit = {}
) {
    Box(
        modifier = modifier
            .background(
                color = containerColor,
                shape = shape
            )
            .clickable { onClick() }
            .padding(padding),
        contentAlignment = contentAlignment
    ) {
        content()
    }
}

@Composable
fun DualButton(
    firstTitle: String,
    onFirstClick: () -> Unit,
    secondTitle: String,
    onSecondClick: () -> Unit,
    isFirstActive: Boolean,
    modifier: Modifier = Modifier
) {
    val activeColors = ButtonColors(
        colorResource(R.color.dark_text),
        colorResource(R.color.light_background),
        colorResource(R.color.dark_text),
        colorResource(R.color.light_background)
    )
    val nonActiveColors = ButtonColors(
        colorResource(R.color.dark_background),
        colorResource(R.color.light_text),
        colorResource(R.color.dark_background),
        colorResource(R.color.light_text)
    )
    val firstColors: ButtonColors
    val secondColors: ButtonColors
    if (isFirstActive) {
        firstColors = activeColors
        secondColors = nonActiveColors
    } else {
        firstColors = nonActiveColors
        secondColors = activeColors
    }
    Row(modifier = modifier) {
        Button(
            {
                onFirstClick()
            },
            modifier = Modifier
                .fillMaxWidth(0.5f),
            colors = firstColors,
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(100, 0, 0, 100),
        ) {
            Text(firstTitle, textAlign = TextAlign.Center,
                style = TextStyles.button(color = Color.Unspecified), softWrap = false)
        }
        Button(
            {
                onSecondClick()
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = secondColors,
            contentPadding = PaddingValues(0.dp),
            shape = RoundedCornerShape(0, 100, 100, 0)
        ) {
            Text(secondTitle, textAlign = TextAlign.Center,
                style = TextStyles.button(color = Color.Unspecified), softWrap = false)
        }
    }
}
