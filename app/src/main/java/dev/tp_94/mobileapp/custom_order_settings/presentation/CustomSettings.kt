package dev.tp_94.mobileapp.custom_order_settings.presentation

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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingAddEditable
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingNew

@Composable
private fun LabeledCheckbox(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    label: String,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            checked = checked, onCheckedChange = onCheckedChange,
            colors = CheckboxColors(
                checkedCheckmarkColor = colorResource(R.color.background),
                uncheckedCheckmarkColor = colorResource(R.color.background),
                checkedBoxColor = colorResource(R.color.dark_accent),
                uncheckedBoxColor = colorResource(R.color.background),
                disabledCheckedBoxColor = colorResource(R.color.middle_text),
                disabledUncheckedBoxColor = colorResource(R.color.light_text),
                disabledIndeterminateBoxColor = colorResource(R.color.middle_text),
                checkedBorderColor = colorResource(R.color.dark_accent),
                uncheckedBorderColor = colorResource(R.color.dark_accent),
                disabledBorderColor = colorResource(R.color.middle_text),
                disabledUncheckedBorderColor = colorResource(R.color.middle_text),
                disabledIndeterminateBorderColor = colorResource(R.color.middle_text),
            ),
            modifier = modifier.padding(end = 4.dp),
        )
        Text(
            text = label,
            style = TextStyles.regular(colorResource(R.color.dark_text)),
        )
    }
}

@Composable
private fun NumberField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    backgroundColor: Color = colorResource(R.color.dark_background),
    modifier: Modifier = Modifier
) {
    var textFieldValue by remember {
        mutableStateOf(value)
    }

    BasicTextField(
        value = value,
        onValueChange = { if (it.isEmpty() || it.toIntOrNull() != null) onValueChange(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
        modifier = modifier
            .background(
                backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(48.dp),
        textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (textFieldValue.isEmpty()) {
                    Text(
                        label,
                        style = TextStyles.regular(colorResource(R.color.light_text)),
                    )
                }
                innerTextField()
            }
        },
        singleLine = true
    )
}

@Composable
private fun FillingField(
    value: String,
    onValueChange: (String) -> Unit,
    backgroundColor: Color = colorResource(R.color.dark_background),
    modifier: Modifier = Modifier
) {
    val textFieldValue by remember {
        mutableStateOf(value)
    }

    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
        modifier = modifier
            .background(
                backgroundColor,
                shape = RoundedCornerShape(8.dp)
            )
            .fillMaxWidth()
            .height(48.dp),
        textStyle = TextStyles.regular(colorResource(R.color.middle_text)),
        decorationBox = { innerTextField ->
            Box(
                Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 0.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                if (textFieldValue.isEmpty()) {
                    Text(
                        "Начинка",
                        style = TextStyles.regular(colorResource(R.color.light_text)),
                    )
                }
                innerTextField()
            }
        },
        singleLine = true
    )
}

@Composable
private fun SectionHeader(text: String) {
    Text(
        text = text,
        style = TextStyles.secondHeader(colorResource(R.color.dark_text)),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    )
}

@Composable
private fun CustomSettingsStatelessScreen(
    state: CustomSettingsState,
    onCustomAccept: (Boolean) -> Unit,
    onImageAccept: (Boolean) -> Unit,
    onShapeAccept: (Boolean) -> Unit,
    onUpdateMinDiameter: (String) -> Unit,
    onUpdateMaxDiameter: (String) -> Unit,
    onUpdateMinWorkPeriod: (String) -> Unit,
    onUpdateMaxWorkPeriod: (String) -> Unit,
    onUpdateNewFilling: (String) -> Unit,
    onUpdateFillings: (List<String>) -> Unit,
    topBar: @Composable () -> Unit
) {
    Scaffold(topBar = topBar) { internalPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(R.color.background)
                )
                .verticalScroll(rememberScrollState())
                .padding(internalPadding)
                .padding(16.dp, 0.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(Modifier.height(42.dp))
            LabeledCheckbox(
                checked = state.isCustomAcceptable,
                onCheckedChange = onCustomAccept,
                label = "Я принимаю индивидуальные заказы"
            )
            Spacer(Modifier.height(42.dp))
            SectionHeader(text = "Диаметр")
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                NumberField(
                    value = state.minDiameter,
                    onValueChange = onUpdateMinDiameter,
                    label = "Минимальный",
                    backgroundColor = colorResource(R.color.dark_background),
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = state.maxDiameter,
                    onValueChange = onUpdateMaxDiameter,
                    label = "Максимальный",
                    backgroundColor = colorResource(R.color.dark_background),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(12.dp))
            SectionHeader(text = "Срок выполнения заказа")
            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                NumberField(
                    value = state.minWorkPeriod,
                    onValueChange = onUpdateMinWorkPeriod,
                    label = "Минимальный",
                    backgroundColor = colorResource(R.color.dark_background),
                    modifier = Modifier.weight(1f)
                )
                NumberField(
                    value = state.maxWorkPeriod,
                    onValueChange = onUpdateMaxWorkPeriod,
                    label = "Максимальный",
                    backgroundColor = colorResource(R.color.dark_background),
                    modifier = Modifier.weight(1f)
                )
            }
            Spacer(Modifier.height(18.dp))

            Row(verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start) {
                FillingField(value = state.newFilling, onValueChange = onUpdateNewFilling,
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 8.dp))
                FillingNew(onClick = {
                    onUpdateFillings(state.fillings + state.newFilling)
                    onUpdateNewFilling("")
                })
            }

            Spacer(Modifier.height(18.dp))
            LazyRow (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                items(state.fillings.size) { index ->
                    val filling = state.fillings[index]
                    FillingAddEditable(
                        text = filling,
                        onDelete = { onUpdateFillings(state.fillings - filling) }
                    )
                }
            }

            Spacer(Modifier.height(18.dp))
            LabeledCheckbox(
                checked = state.isImageAcceptable,
                onCheckedChange = onImageAccept,
                label = "Я делаю изображения на торте"
            )
            Spacer(Modifier.height(8.dp))
            LabeledCheckbox(
                checked = state.isShapeAcceptable,
                onCheckedChange = onShapeAccept,
                label = "Я делаю торты индивидуальной формы"
            )
        }
    }
}

@Composable
fun CustomSettingsStatefulScreen(viewModel: CustomSettingsViewModel = hiltViewModel(),
                                 onError: () -> Unit,
                                 onSave: () -> Unit,
                                 topBar: @Composable () -> Unit) {
    val state = viewModel.state.collectAsStateWithLifecycle()
    CustomSettingsStatelessScreen(
        state = state.value,
        onCustomAccept = { viewModel.updateCustomAcceptable(it) },
        onImageAccept = { viewModel.updateImageAcceptable(it) },
        onShapeAccept = { viewModel.updateShapeAcceptable(it) },
        onUpdateMinDiameter = { viewModel.updateMinDiameter(it) },
        onUpdateMaxDiameter = { viewModel.updateMaxDiameter(it) },
        onUpdateMinWorkPeriod = { viewModel.updateMinWorkPeriod(it) },
        onUpdateMaxWorkPeriod = { viewModel.updateMaxWorkPeriod(it) },
        onUpdateNewFilling = { viewModel.updateNewFilling(it) },
        onUpdateFillings = { viewModel.updateFillings(it) },
        topBar = topBar
    )
}

@Preview(showBackground = true)
@Composable
fun CustomSettingsPreview() {
    val testFillings = listOf(
        "Шоколадная",
        "Ванильная",
        "Карамельная",
        "Ягодная",
        "Ореховая",
        "Кокосовая",
        "Клубничная",
        "Лимонная"
    )

    val state = remember {
        mutableStateOf(
            CustomSettingsState(
                isCustomAcceptable = true,
                isImageAcceptable = false,
                isShapeAcceptable = true,
                minDiameter = "15",
                maxDiameter = "30",
                minWorkPeriod = "1",
                maxWorkPeriod = "14",
                newFilling = "Какао",
                fillings = testFillings
            )
        )
    }

    CustomSettingsStatelessScreen(
        state = state.value,
        onCustomAccept = { state.value = state.value.copy(isCustomAcceptable = it) },
        onImageAccept = { state.value = state.value.copy(isImageAcceptable = it) },
        onShapeAccept = { state.value = state.value.copy(isShapeAcceptable = it) },
        onUpdateMinDiameter = { state.value = state.value.copy(minDiameter = it) },
        onUpdateMaxDiameter = { state.value = state.value.copy(maxDiameter = it) },
        onUpdateMinWorkPeriod = { state.value = state.value.copy(minWorkPeriod = it) },
        onUpdateMaxWorkPeriod = { state.value = state.value.copy(maxWorkPeriod = it) },
        onUpdateNewFilling = { state.value = state.value.copy(newFilling = it) },
        onUpdateFillings = { state.value = state.value.copy(fillings = it) },
        topBar = { TopNameBar("Кастомизация") {} }
    )
}