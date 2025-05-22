package dev.tp_94.mobileapp.self_made_cake.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.darken
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles

@Composable
fun HsvDialog(
    onDismissRequest: () -> Unit,
    onConfirmation: (Color) -> Unit,
    initialColor: Color = Color(255, 255, 255)
) {
    val color = remember { mutableStateOf(initialColor) }
    val colorPickerController = ColorPickerController()
    colorPickerController.enabled = true
    Dialog(onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize(),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = colorResource(R.color.background))
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(0.dp, 24.dp, 0.dp, 0.dp),
                    text = "Выбор цвета",
                    style = TextStyles.header(colorResource(R.color.dark_text))
                )
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(20.dp, 0.dp),
                    controller = colorPickerController,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        color.value = colorEnvelope.color
                    },
                    initialColor = initialColor
                )
                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(40.dp)
                        .padding(5.dp, 0.dp, 5.dp, 5.dp),
                    borderColor = color.value.darken(),
                    controller = colorPickerController
                )
                Spacer(modifier = Modifier.height(7.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        modifier = Modifier,
                        text = "Выбраный цвет:",
                        style = TextStyles.regular(colorResource(R.color.dark_text))
                    )
                    Spacer(Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(
                                color.value.darken(),
                                shape = RoundedCornerShape(8.dp)
                            )
                            .padding(4.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .fillMaxSize()
                                .background(
                                    color.value,
                                    shape = RoundedCornerShape(4.dp)
                                )
                        )
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    DiscardButton(
                        onClick = { onDismissRequest() },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text(
                            "Отмена",
                            style = TextStyles.button(colorResource(R.color.dark_text))
                        )
                    }
                    ActiveButton(
                        onClick = { onConfirmation(color.value) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "ОК",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun HsvDialogPreview() {
    MaterialTheme {
        HsvDialog(
            onDismissRequest = {},
            onConfirmation = {}
        )
    }
}