package dev.tp_94.mobileapp.core.themes

import androidx.annotation.IntRange
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import dev.tp_94.mobileapp.R

@Composable
fun AccentSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    @IntRange(from = 0)
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {
    Slider(
        value,
        onValueChange,
        modifier,
        enabled,
        valueRange,
        steps,
        onValueChangeFinished,
        SliderDefaults.colors(
            activeTrackColor = colorResource(R.color.accent),
            inactiveTrackColor = colorResource(R.color.light_text),
            activeTickColor = colorResource(R.color.light_accent),
            inactiveTickColor = colorResource(R.color.middle_text),
            thumbColor = colorResource(R.color.accent)
        ),
        interactionSource
    )
}