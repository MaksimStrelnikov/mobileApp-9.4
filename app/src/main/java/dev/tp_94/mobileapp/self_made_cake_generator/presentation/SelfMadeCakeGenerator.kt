package dev.tp_94.mobileapp.self_made_cake_generator.presentation;

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Restrictions
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.custom_order_settings.presentation.components.SectionHeader
import dev.tp_94.mobileapp.self_made_cake.presentation.components.DatePickerButton
import dev.tp_94.mobileapp.self_made_cake.presentation.components.DiameterSlider
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingAddEditable
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingNew
import dev.tp_94.mobileapp.self_made_cake.presentation.components.TextEditor
import dev.tp_94.mobileapp.self_made_cake_generator.presentation.components.GeneratedImage


@Composable
fun SelfMadeCakeGeneratorStatefulScreen(
    viewModel: SelfMadeCakeGeneratorViewModel = hiltViewModel(),
    onDone: () -> Unit,
    onError: () -> Unit,
    topBar: @Composable () -> Unit
) {
    val session = viewModel.session.collectAsStateWithLifecycle()
    LaunchedEffect(Unit) {
        if (session.value == null || session.value!!.user !is Customer) {
            onError()
            viewModel.exit()
        }
    }
    val state by viewModel.state.collectAsState()
    SelfMadeCakeGeneratorStatelessScreen(
        state = state,
        onDiameterChange = { viewModel.setDiameter(it) },
        onGenerate = { viewModel.generateImage() },
        onPromptChange = { viewModel.updatePrompt(it) },
        onCommentChange = { viewModel.updateComment(it) },
        topBar = topBar,
        onSend = {
            viewModel.sendCustomCake(onDone)
        },
        onUpdateFillings = { viewModel.updateFillings(it) }
    )
}

@Composable
fun SelfMadeCakeGeneratorStatelessScreen(
    state: SelfMadeCakeGeneratorState,
    onDiameterChange: (Float) -> Unit,
    onGenerate: () -> Unit,
    onPromptChange: (String) -> Unit,
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
            GeneratedImage(imageUrl = state.cakeCustom.imageUrl)
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .background(
                        colorResource(R.color.light_background), shape = RoundedCornerShape(8.dp)
                    )
                    .width(360.dp)
                    .wrapContentHeight()
                    .padding(19.dp, 17.dp)
            ) {
                Column {
                    BasicTextField(state.prompt,
                        onPromptChange,
                        enabled = true,
                        modifier = Modifier
                            .background(
                                colorResource(R.color.background), shape = RoundedCornerShape(8.dp)
                            )
                            .border(
                                BorderStroke(
                                    color = colorResource(R.color.dark_background), width = 4.dp
                                ), shape = RoundedCornerShape(8.dp)
                            )
                            .padding(9.dp, 8.dp)
                            .fillMaxWidth()
                            .height(70.dp),
                        textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
                        decorationBox = { innerTextField ->
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .padding(4.dp)
                            ) {
                                if (state.prompt.isEmpty()) {
                                    Text(
                                        "Введите текст...",
                                        style = TextStyles.regular(colorResource(R.color.light_text))
                                    )
                                }
                                innerTextField()
                            }
                        })
                    Spacer(modifier = Modifier.height(8.dp))
                    DiscardButton(
                        {
                            onGenerate()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp)
                    )
                    {
                        if (state.isGenerating) Text("Подождите..." ,  style = TextStyles.button(colorResource(R.color.dark_text)))
                        else Text("Сгенерировать" ,  style = TextStyles.button(colorResource(R.color.dark_text)))
                    }
                    Text(
                        "* Приготовленный торт может не полностью " +
                                "соответсвовать сгенерированному изображению ",
                        style = TextStyles.regular(colorResource(R.color.light_text), fontSize = 14.sp),
                        modifier = Modifier.padding(0.dp, 16.dp, 0.dp, 8.dp)
                    )
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
                        modifier = Modifier
                            .fillMaxWidth(0.8f)
                            .background(colorResource(R.color.light_background)),
                    ) {
                        val fillings = state.restrictions.fillings
                        fillings.subtract(state.cakeCustom.fillings).toList().forEach { filling ->
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

                SectionHeader("Выбор начинки")
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
            DatePickerButton(modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
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
        }
    }
}

@Preview
@Composable
fun PreviewSelfMadeCakeGeneratorStatelessScreen() {
    val c = Confectioner(
        id = 1,
        name = "TODO()",
        phoneNumber = "TODO()",
        email = "TODO()",
        description = "TODO()",
        address = "TODO()"
    )
    val state = remember {
        mutableStateOf(
            SelfMadeCakeGeneratorState(
                cakeCustom = CakeCustom(Color.Cyan, 10f, confectioner = c,
                    fillings = listOf("Ягодный", "Ореховый", "Кокосовый", "Клубничный", "Лимонный")),
                confectioner = c,
                restrictions = Restrictions()
            )
        )
    }

    MaterialTheme {
        SelfMadeCakeGeneratorStatelessScreen(
            state = state.value,
            onDiameterChange = {},
            onPromptChange = {state.value = state.value.copy(prompt = it)},
            //TODO: make onGenerate work at the ViewModel
            onGenerate = { state.value = state.value.copy(prompt = "Я сгенерировал торт") },
            onCommentChange = {},
            onSend = { },
            topBar = { TopNameBar("Дизайн торта") { } },
            onUpdateFillings = {}
        )
    }

}
