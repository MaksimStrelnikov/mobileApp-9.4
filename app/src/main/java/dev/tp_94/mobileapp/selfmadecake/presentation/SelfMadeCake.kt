package dev.tp_94.mobileapp.selfmadecake.presentation

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
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.fastRoundToInt
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.github.skydoves.colorpicker.compose.BrightnessSlider
import com.github.skydoves.colorpicker.compose.ColorEnvelope
import com.github.skydoves.colorpicker.compose.ColorPickerController
import com.github.skydoves.colorpicker.compose.HsvColorPicker
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.darken
import dev.tp_94.mobileapp.core.themes.AccentSlider
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.Fonts
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.selfmadecake.presentation.components.ImageAddition
import dev.tp_94.mobileapp.selfmadecake.presentation.components.InteractableImage
import dev.tp_94.mobileapp.selfmadecake.presentation.components.InteractableText
import dev.tp_94.mobileapp.selfmadecake.presentation.components.TextEditor
import java.util.Locale
import kotlin.math.ceil
import kotlin.math.pow

@Composable
fun SelfMadeCakeScreen(viewModel: SelfMadeCakeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                colorResource(R.color.background)
            )
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(8.dp))
        Box(
            modifier = Modifier
                .width(360.dp)
                .height(460.dp)
                .background(
                    state.cake.color.darken(),
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.Center,
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(270.dp)
                        .clip(CircleShape)
                        .background(state.cake.color)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .height(70.dp)
                        .background(state.cake.color)
                )
            }
            InteractableImage(
                imageUri = state.cake.imageUri,
                imageOffset = state.cake.imageOffset,
                onOffsetChanged = { viewModel.updateImageOffset(it) }
            )
            InteractableText(
                text = state.cake.text,
                textOffset = state.cake.textOffset,
                textStyle = TextStyles.header(colorResource(R.color.dark_accent)),
                onOffsetChanged = { viewModel.updateTextOffset(it) }
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        ActiveButton(
            onClick = { viewModel.openColorPicker() },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Выбрать цвет",
                fontFamily = Fonts.robotoSlab,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                letterSpacing = 0.20.sp,
                lineHeight = 24.sp
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
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
                    "Диаметр торта",
                    fontFamily = Fonts.robotoSlab,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.SemiBold,
                    lineHeight = 20.sp,
                    letterSpacing = 0.sp,
                    color = colorResource(R.color.dark_text)
                )
                AccentSlider(
                    state.cake.diameter,
                    { viewModel.setDiameter(it) },
                    valueRange = 10f..40f,
                    steps = (40 - 10) / 5 - 1,
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
                                state.cake.diameter.fastRoundToInt()
                            ),
                            fontFamily = Fonts.robotoSlab,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 14.sp,
                            letterSpacing = 0.sp,
                            color = colorResource(R.color.dark_text)
                        )

                        Text(
                            String.format(
                                Locale.ROOT,
                                String.format(
                                    Locale.ROOT,
                                    "~%d-%d кг",
                                    (state.cake.diameter.pow(2) * 2 / 400).fastRoundToInt(),
                                    ceil(state.cake.diameter.pow(2) * 2.5 / 400).fastRoundToInt()
                                ),
                                state.cake.diameter.fastRoundToInt()
                            ),
                            fontFamily = Fonts.robotoSlab,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Normal,
                            lineHeight = 14.sp,
                            letterSpacing = 0.sp,
                            color = colorResource(R.color.light_text),
                            modifier = Modifier
                                .padding(15.dp, 0.dp)
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(9.dp))
        TextEditor({ viewModel.updateText(it) }, state.cake.text, "Редактировать текст")
        ImageAddition( { viewModel.updateImage(it) })
        TextEditor({ viewModel.updateComment(it) }, state.cake.comment, "Комментарий к заказу")

        when {
            state.colorPickerOpen -> {
                HsvDialog(
                    { viewModel.closeColorPicker() },
                    {
                        viewModel.setColor(it)
                        viewModel.closeColorPicker()
                    },
                    state.cake.color
                )
            }
        }
    }
}

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
                .wrapContentSize()
                .padding(16.dp),
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
                    fontFamily = Fonts.robotoSlab,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 20.sp,
                    color = colorResource(R.color.dark_text)
                )
                HsvColorPicker(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(350.dp)
                        .padding(10.dp, 0.dp, 10.dp, 0.dp),
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
                        fontFamily = Fonts.robotoSlab,
                        fontWeight = FontWeight.Normal,
                        fontSize = 14.sp,
                        color = colorResource(R.color.dark_text)
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
                            fontFamily = Fonts.robotoSlab,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            letterSpacing = 0.20.sp,
                            lineHeight = 24.sp
                        )
                    }
                    ActiveButton(
                        onClick = { onConfirmation(color.value) },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.padding(8.dp),
                    ) {
                        Text(
                            "ОК",
                            fontFamily = Fonts.robotoSlab,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            letterSpacing = 0.20.sp,
                            lineHeight = 24.sp
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
