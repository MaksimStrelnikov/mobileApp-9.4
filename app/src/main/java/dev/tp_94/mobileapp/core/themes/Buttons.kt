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

    val currentBackgroundColor = if (isClicked) textColor else backgroundColor
    val currentTextColor = if (isClicked) backgroundColor else textColor

    Button(
        onClick = {
            onClick()
            if (!isClicked) isClicked = true
        },
        modifier,
        enabled,
        shape,
        colors = ButtonColors(
            containerColor = currentBackgroundColor,
            contentColor = currentTextColor,
            disabledContainerColor = currentBackgroundColor,
            disabledContentColor = currentTextColor
        ),
        elevation,
        border,
        contentPadding,
        interactionSource
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
                Text(text = if (isClicked) "В корзине" else text,
                    color = if (isClicked) backgroundColor else textColor,
                    textAlign = TextAlign.Center)
            }
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
