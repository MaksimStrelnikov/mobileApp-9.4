package dev.tp_94.mobileapp.core.themes


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import dev.tp_94.mobileapp.R

@Composable
fun ActiveButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    shape: Shape = ButtonDefaults.shape,
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
    shape: Shape = ButtonDefaults.shape,
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
            shape = RoundedCornerShape(100, 0, 0, 100),
        ) {
            Text(firstTitle, textAlign = TextAlign.Center, style = TextStyles.regularNoColor())
        }
        Button(
            {
                onSecondClick()
            },
            modifier = Modifier
                .fillMaxWidth(),
            colors = secondColors,
            shape = RoundedCornerShape(0, 100, 100, 0)
        ) {
            Text(secondTitle, textAlign = TextAlign.Center, style = TextStyles.regularNoColor())
        }
    }
}
