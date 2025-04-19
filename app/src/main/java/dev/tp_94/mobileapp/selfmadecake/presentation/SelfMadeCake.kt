package dev.tp_94.mobileapp.selfmadecake.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.darken
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DualButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.selfmadecake.presentation.components.DiameterSlider
import dev.tp_94.mobileapp.selfmadecake.presentation.components.HsvDialog
import dev.tp_94.mobileapp.selfmadecake.presentation.components.ImageAddition
import dev.tp_94.mobileapp.selfmadecake.presentation.components.InteractableImage
import dev.tp_94.mobileapp.selfmadecake.presentation.components.InteractableText
import dev.tp_94.mobileapp.selfmadecake.presentation.components.TextEditor

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
            .padding(26.dp, 16.dp),
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
                        .background(state.cake.color),
                    contentAlignment = Alignment.Center
                ) {
                    InteractableImage(
                        imageUri = state.cake.imageUri,
                        imageOffset = state.cake.imageOffset,
                        onOffsetChanged = { viewModel.updateImageOffset(it) }
                    )
                    InteractableText(
                        text = state.cake.text,
                        textOffset = state.cake.textOffset,
                        textStyle = TextStyles.header(colorResource(R.color.dark_text)),
                        onOffsetChanged = { viewModel.updateTextOffset(it) }
                    )
                }
                Spacer(modifier = Modifier.height(30.dp))
                Box(
                    modifier = Modifier
                        .width(250.dp)
                        .height(70.dp)
                        .background(state.cake.color)
                )
            }
        }
        Spacer(modifier = Modifier.height(24.dp))
        ActiveButton(
            modifier = Modifier
            .fillMaxWidth(),
            onClick = { viewModel.openColorPicker() },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Выбрать цвет",
                style = TextStyles.button(colorResource(R.color.light_background))
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        DiameterSlider(
            onChange = { viewModel.setDiameter(it) },
            diameter = state.cake.diameter,
            valueRange = 10f..40f
        )
        Spacer(modifier = Modifier.height(9.dp))
        Box(
            modifier = Modifier
                .background(
                    color = colorResource(R.color.light_background),
                    shape = RoundedCornerShape(8.dp)
                )
                .padding(14.dp, 17.dp, 14.dp, 0.dp)
                .fillMaxWidth()
                .wrapContentHeight()
        ) {
            Column (
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DualButton(
                    firstTitle = "Изображение",
                    onFirstClick = { viewModel.setTextImageEditor(Editor.IMAGE) },
                    secondTitle = "Текст",
                    onSecondClick = { viewModel.setTextImageEditor(Editor.TEXT) },
                    isFirstActive = state.textImageEditor == Editor.IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                )
                when (state.textImageEditor) {
                    Editor.IMAGE -> {
                        ImageAddition(onAdd = { viewModel.updateImage(it) }, state.cake.imageUri)
                    }
                    Editor.TEXT -> {
                        TextEditor(
                            { viewModel.updateText(it) },
                            state.cake.text,
                            "Редактировать текст"
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(9.dp))
        TextEditor({ viewModel.updateComment(it) }, state.cake.comment, "Комментарий кондитеру")
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

