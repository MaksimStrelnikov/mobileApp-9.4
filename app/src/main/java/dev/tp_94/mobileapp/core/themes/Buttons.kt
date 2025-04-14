package dev.tp_94.mobileapp.core.themes


import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ButtonElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.colorResource
import dev.tp_94.mobileapp.R

@Composable
fun ActiveButton(onClick: () -> Unit,
                 modifier: Modifier = Modifier,
                 enabled: Boolean = true,
                 shape: Shape = ButtonDefaults.shape,
                 elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
                 border: BorderStroke? = null,
                 contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
                 interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
                 content: @Composable () -> Unit = {}) {
    val buttonColors = ButtonColors(
        colorResource(id = R.color.accent2),
        colorResource(id = R.color.light_background),
        colorResource(id = R.color.dark_background),
        colorResource(id = R.color.middle_text)
    )
    
    Button(
        onClick, modifier, enabled, shape, buttonColors, elevation, border, contentPadding, interactionSource
    ) { content() }
}

@Composable
fun DiscardButton(onClick: () -> Unit,
                 modifier: Modifier = Modifier,
                 enabled: Boolean = true,
                 shape: Shape = ButtonDefaults.shape,
                 elevation: ButtonElevation? = ButtonDefaults.buttonElevation(),
                 border: BorderStroke? = null,
                 contentPadding: PaddingValues = ButtonDefaults.ContentPadding,
                 interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
                 content: @Composable () -> Unit = {}) {
    val discardButtonColors = ButtonColors(
        colorResource(id = R.color.dark_background),
        colorResource(id = R.color.middle_text),
        colorResource(id = R.color.dark_background),
        colorResource(id = R.color.middle_text)
    )

    Button(
        onClick, modifier, enabled, shape, discardButtonColors, elevation, border, contentPadding, interactionSource
    ) { content() }
}