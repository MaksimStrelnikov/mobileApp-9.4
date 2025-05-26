package dev.tp_94.mobileapp.self_made_cake.presentation

import android.net.Uri
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Restrictions
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.DualButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.custom_order_settings.presentation.components.SectionHeader
import dev.tp_94.mobileapp.self_made_cake.presentation.components.DatePickerButton
import dev.tp_94.mobileapp.self_made_cake.presentation.components.DiameterSlider
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingAddEditable
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingNew
import dev.tp_94.mobileapp.self_made_cake.presentation.components.HsvDialog
import dev.tp_94.mobileapp.self_made_cake.presentation.components.ImageAddition
import dev.tp_94.mobileapp.self_made_cake.presentation.components.InteractableImage
import dev.tp_94.mobileapp.self_made_cake.presentation.components.InteractableText
import dev.tp_94.mobileapp.self_made_cake.presentation.components.TextEditor

@Composable
fun SelfMadeCakeStatefulScreen(
    viewModel: SelfMadeCakeViewModel = hiltViewModel(),
    onDone: () -> Unit,
    onError: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val user = viewModel.getUser()
    LaunchedEffect(user) {
        if (user == null || user !is Customer) {
            onError()
            viewModel.exit()
        }
    }
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
        topBar = topBar,
        onSend = {
            viewModel.sendCustomCake(onDone)
        },
        onUpdateFillings = { viewModel.updateFillings(it) }
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
    onImageChange: (String?) -> Unit,
    onCommentChange: (String) -> Unit,
    onSend: () -> Unit,
    onUpdateFillings: (List<String>) -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(R.color.background)
                )
                .padding(innerPadding)
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
                        state.cakeCustom.color.darken(),
                        shape = RoundedCornerShape(8.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Box(
                        modifier = Modifier
                            .size(270.dp)
                            .clip(CircleShape)
                            .background(state.cakeCustom.color),
                        contentAlignment = Alignment.Center
                    ) {
                        InteractableImage(
                            imageUrl = state.cakeCustom.imageUrl,
                            imageOffset = state.cakeCustom.imageOffset,
                            onOffsetChanged = onImageDrag
                        )
                        InteractableText(
                            text = state.cakeCustom.text,
                            textOffset = state.cakeCustom.textOffset,
                            textStyle = TextStyles.header(colorResource(R.color.dark_text)),
                            onOffsetChanged = onTextDrag
                        )
                    }
                    Spacer(modifier = Modifier.height(30.dp))
                    Box(
                        modifier = Modifier
                            .width(250.dp)
                            .height(70.dp)
                            .background(state.cakeCustom.color)
                    )
                }
            }
            Spacer(modifier = Modifier.height(16.dp))
            DiscardButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = onColorChangeDialogOpen,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Выбрать цвет",
                    style = TextStyles.button(colorResource(R.color.dark_text))
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            ActiveButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(60.dp),
                onClick = onColorChangeDialogOpen,
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    "Генерация торта",
                    style = TextStyles.button(colorResource(R.color.light_background))
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
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
                    //TODO: make dependent of restrictions isImageAcceptable
                    DualButton(
                        firstTitle = "Фото",
                        onFirstClick = onOpenImageChangeClick,
                        secondTitle = "Текст",
                        onSecondClick = onOpenTextChangeClick,
                        isFirstActive = state.textImageEditor == Editor.IMAGE,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp)
                    )
                    when (state.textImageEditor) {
                        Editor.IMAGE -> {
                            ImageAddition(
                                onAdd = onImageChange,
                                imageUrl = state.cakeCustom.imageUrl
                            )
                        }

                        Editor.TEXT -> {
                            TextEditor(
                                onChange = onTextChange,
                                text = state.cakeCustom.text,
                                header = "Редактировать текст"
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            DiameterSlider(
                onChange = onDiameterChange,
                diameter = state.cakeCustom.diameter,
                valueRange = state.restrictions.minDiameter..state.restrictions.maxDiameter
            )
            Spacer(modifier = Modifier.height(8.dp))
            val expandedState = remember { mutableStateOf(false) }
            val expanded = expandedState.value
            fun updateExpanded(newValue: Boolean) { expandedState.value = newValue }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(modifier = Modifier.wrapContentWidth()) {
                    FillingNew(
                        onClick = { updateExpanded(true) }
                    )

                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { updateExpanded(false) },
                        modifier = Modifier.fillMaxWidth(0.8f)
                        .background(colorResource(R.color.light_background)),
                    ) {
                        //TODO: replace with state.confectioner.fillings
                        val fillings = state.restrictions.fillings
                        fillings.subtract(state.cakeCustom.fillings.toSet()).toList().forEach { filling ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = filling,
                                        style = TextStyles.regular(color = colorResource(R.color.dark_text)),
                                        modifier = Modifier.padding(8.dp)
                                    )
                                },
                                onClick = {
                                    if (!state.cakeCustom.fillings.contains(filling)) {
                                        onUpdateFillings(state.cakeCustom.fillings + filling)
                                    }
                                    updateExpanded(false)
                                }
                            )
                        }
                    }
                }
                LazyRow (
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    items(state.cakeCustom.fillings.size) { index ->
                        val filling = state.cakeCustom.fillings[index]
                        FillingAddEditable(
                            text = filling,
                            onDelete = { onUpdateFillings(state.cakeCustom.fillings - filling) }
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            SectionHeader("Выбор начинки")
            DatePickerButton(modifier = Modifier.fillMaxWidth().height(48.dp),
                minDaysFromToday = state.restrictions.minPreparationDays,)
            Spacer(modifier = Modifier.height(8.dp))
            TextEditor(
                onChange = onCommentChange,
                text = state.cakeCustom.description,
                header = "Комментарий кондитеру"
            )
            Spacer(modifier = Modifier.height(8.dp))
            if (!(state.error == null || state.error == "")) {
                Text(
                    state.error,
                    style = TextStyles.regular(colorResource(R.color.dark_accent))
                )
            }
            ActiveButton(
                onClick = onSend,
                modifier = Modifier.fillMaxWidth()
            ) {
                when(state.isLoading) {
                    false -> {
                        Text(
                            text = "Отправить",
                            style = TextStyles.button(colorResource(R.color.light_background))
                        )
                    }
                    true -> {
                        CircularProgressIndicator(color = colorResource(R.color.light_background))
                    }
                }
            }
            when {
                state.colorPickerOpen -> {
                    HsvDialog(
                        onDismissRequest = onColorChangeDialogDismiss,
                        onConfirmation = onColorChangeDialogSave,
                        initialColor = state.cakeCustom.color
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewSelfMadeCakeStatelessScreen() {
    val c = Confectioner(
        id = 1,
        name = "TODO()",
        phoneNumber = "TODO()",
        email = "TODO()",
        description = "TODO()",
        address = "TODO()"
    )
    MaterialTheme {
        SelfMadeCakeStatelessScreen(
            state = SelfMadeCakeState(
                cakeCustom = CakeCustom(Color.Cyan, 10f, confectioner = c),
                restrictions = Restrictions(
                    isImageAcceptable = true,
                    fillings = listOf("Ягодный", "Ореховый", "Кокосовый", "Клубничный", "Лимонный")
                ),
            ),
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
            onCommentChange = {},
            onSend = { },
            topBar = { TopNameBar("Дизайн торта") { } },
            onUpdateFillings = {}
        )
    }

}

