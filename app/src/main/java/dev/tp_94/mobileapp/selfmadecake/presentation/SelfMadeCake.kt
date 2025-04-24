package dev.tp_94.mobileapp.selfmadecake.presentation

import android.content.res.Resources
import android.net.Uri
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.darken
import dev.tp_94.mobileapp.core.models.Cake
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
fun SelfMadeCakeStatefulScreen(viewModel: SelfMadeCakeViewModel = hiltViewModel()) {
    val state by viewModel.state.collectAsState()
    SelfMadeCakeStatelessScreen(
        state = state,
        onImageDrag = { viewModel.updateImageOffset(it) },
        onTextDrag = { viewModel.updateTextOffset(it) },
        onColorChangeDialogOpen = { viewModel.openColorPicker() },
        onColorChangeDialogDismiss = { viewModel.closeColorPicker() },
        onColorChangeDialogSave = {
            viewModel.setColor(it)
            viewModel.closeColorPicker()
        },
        onDiameterChange = { viewModel.setDiameter(it) },
        onOpenTextChangeClick = { viewModel.setTextImageEditor(Editor.TEXT) },
        onOpenImageChangeClick = { viewModel.setTextImageEditor(Editor.IMAGE) },
        onTextChange = { viewModel.updateText(it) },
        onImageChange = { viewModel.updateImage(it) },
        onCommentChange = { viewModel.updateComment(it) },
    )
}

@Composable
fun SelfMadeCakeStatelessScreen(
    state: SelfMadeCakeState,
    onImageDrag: (Offset) -> Unit,
    onTextDrag: (Offset) -> Unit,
    onColorChangeDialogOpen: () -> Unit,
    onColorChangeDialogDismiss: () -> Unit,
    onColorChangeDialogSave: (Color) -> Unit,
    onDiameterChange: (Float) -> Unit,
    onOpenTextChangeClick: () -> Unit,
    onOpenImageChangeClick: () -> Unit,
    onTextChange: (String) -> Unit,
    onImageChange: (Uri?) -> Unit,
    onCommentChange: (String) -> Unit,
) {
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
                        onOffsetChanged = onImageDrag
                    )
                    InteractableText(
                        text = state.cake.text,
                        textOffset = state.cake.textOffset,
                        textStyle = TextStyles.header(colorResource(R.color.dark_text)),
                        onOffsetChanged = onTextDrag
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
            onClick = onColorChangeDialogOpen,
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                "Выбрать цвет",
                style = TextStyles.button(colorResource(R.color.light_background))
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
        DiameterSlider(
            onChange = onDiameterChange,
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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                DualButton(
                    firstTitle = "Изображение",
                    onFirstClick = onOpenImageChangeClick,
                    secondTitle = "Текст",
                    onSecondClick = onOpenTextChangeClick,
                    isFirstActive = state.textImageEditor == Editor.IMAGE,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(52.dp)
                )
                when (state.textImageEditor) {
                    Editor.IMAGE -> {
                        ImageAddition(onAdd = onImageChange, imageUri = state.cake.imageUri)
                    }

                    Editor.TEXT -> {
                        TextEditor(
                            onChange = onTextChange,
                            text = state.cake.text,
                            header = "Редактировать текст"
                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(9.dp))
        TextEditor(
            onChange = onCommentChange,
            text = state.cake.comment,
            header = "Комментарий кондитеру"
        )
        when {
            state.colorPickerOpen -> {
                HsvDialog(
                    onDismissRequest = onColorChangeDialogDismiss,
                    onConfirmation = onColorChangeDialogSave,
                    initialColor = state.cake.color
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewSelfMadeCakeStatelessScreen() {
    MaterialTheme {
        SelfMadeCakeStatelessScreen(
            state = SelfMadeCakeState(cake = Cake(Color.Cyan, 10f)),
            onImageDrag = {},
            onTextDrag = {},
            onColorChangeDialogOpen = {},
            onColorChangeDialogDismiss = {},
            onColorChangeDialogSave = {},
            onDiameterChange = {},
            onOpenTextChangeClick = {},
            onOpenImageChangeClick = {},
            onTextChange = {},
            onImageChange = {},
            onCommentChange = {}
        )
    }

}

