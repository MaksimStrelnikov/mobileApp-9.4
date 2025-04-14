package dev.tp_94.mobileapp.selfmadecake.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import dev.tp_94.mobileapp.core.models.Cake
import dev.tp_94.mobileapp.core.themes.Colors
import dev.tp_94.mobileapp.core.themes.Fonts
import dev.tp_94.mobileapp.core.themes.darken
import javax.inject.Inject

@Composable
fun SelfMadeCakeScreen (viewModel: SelfMadeCakeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Colors.BACKGROUND
            )
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .size(360.dp)
                .background(
                    state.cake.color.darken(),
                    shape = RoundedCornerShape(10.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(270.dp)
                    .clip(CircleShape)
                    .background(state.cake.color)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(onClick = { viewModel.openColorPicker() }) {
            Text("Выбрать цвет")
        }

        when {
            state.colorPickerOpen -> {
                HsvDialog(
                    { viewModel.closeColorPicker() },
                    {
                        viewModel.setColor(it)
                        viewModel.closeColorPicker()
                    }
                )
            }
        }
    }
}

@Composable
fun HsvDialog(onDismissRequest: () -> Unit, onConfirmation: (Color) -> Unit) {
    val color = remember { mutableStateOf(Color(255, 255, 255)) }
    val colorPickerController = ColorPickerController()
    colorPickerController.enabled = true
    Dialog(onDismissRequest) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = Colors.BACKGROUND)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    modifier = Modifier
                        .padding(24.dp),
                    text = "Выбор цвета",
                    fontFamily = Fonts.robotoSlab,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                )
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(10.dp),
                    controller = colorPickerController,
                    onColorChanged = { colorEnvelope: ColorEnvelope ->
                        color.value = colorEnvelope.color
                    }
                )
                BrightnessSlider(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(50.dp)
                        .padding(5.dp),
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
                        fontFamily = Fonts.robotoSlab,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp
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
                    TextButton(
                        onClick = { onDismissRequest() },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("Отмена")
                    }
                    Button(
                        onClick = { onConfirmation(color.value) },
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text("ОК")
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
