package dev.tp_94.mobileapp.self_made_cake.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.fastRoundToInt
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.AccentSlider
import dev.tp_94.mobileapp.core.themes.TextStyles
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.pow

@Composable
fun DiameterSlider(onChange: (Float) -> Unit, diameter: Float, valueRange: ClosedFloatingPointRange<Float>) {
    val diameterState by rememberUpdatedState(diameter)
    Box(
        modifier = Modifier
            .background(
                colorResource(R.color.light_background),
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(19.dp, 17.dp)
    ) {
        Column {
            Text(
                "Диаметр торта",
                style = TextStyles.header(colorResource(R.color.dark_text))
            )
            val steps = ceil((valueRange.endInclusive - valueRange.start) / 5).toInt() - 1
            AccentSlider(
                diameterState,
                onChange,
                valueRange = valueRange,
                steps = if (steps <= 0) 0 else steps,
                modifier = Modifier
                    .fillMaxWidth(1f)
                    .padding(23.dp, 0.dp)
            )
            Column(
                modifier = Modifier
                    .padding(30.dp, 0.dp)
            ) {

                Row {
                    Text(
                        String.format(
                            Locale.ROOT,
                            "%d см",
                            diameterState.fastRoundToInt()
                        ),
                        style = TextStyles.regular(colorResource(R.color.dark_text))
                    )

                    Text(
                        String.format(
                            Locale.ROOT,
                            String.format(
                                Locale.ROOT,
                                "~%d-%d кг",
                                (diameterState.pow(2) * 2 / 400).fastRoundToInt(),
                                ceil(diameterState.pow(2) * 2.5 / 400).fastRoundToInt()
                            ),
                            diameterState.fastRoundToInt()
                        ),
                        style = TextStyles.regular(colorResource(R.color.light_text)),
                        modifier = Modifier
                            .padding(15.dp, 0.dp)
                    )
                }
            }
        }
    }
}