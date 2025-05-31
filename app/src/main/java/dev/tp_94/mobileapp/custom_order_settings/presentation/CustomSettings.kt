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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.core.themes.FillingField
import dev.tp_94.mobileapp.custom_order_settings.presentation.components.LabeledCheckbox
import dev.tp_94.mobileapp.core.themes.NumberEditor
import dev.tp_94.mobileapp.core.themes.SpecialTextField
import dev.tp_94.mobileapp.custom_order_settings.presentation.components.SectionHeader
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingAddEditable
import dev.tp_94.mobileapp.self_made_cake.presentation.components.FillingNew

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
    topBar: @Composable () -> Unit,
    bottomBar: @Composable () -> Unit
) {
    Scaffold(
        topBar = topBar,
        bottomBar = bottomBar
    ) { internalPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    colorResource(R.color.background)
                )
                .verticalScroll(rememberScrollState())
                .padding(internalPadding)
                .padding(16.dp, 0.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Spacer(Modifier.height(42.dp))
            LabeledCheckbox(
                checked = state.isCustomAcceptable,
                onCheckedChange = onCustomAccept,
                label = "Я принимаю индивидуальные заказы"
            )
            if (state.isCustomAcceptable) {
                Spacer(Modifier.height(42.dp))
                SectionHeader(text = "Диаметр")
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        NumberEditor(
                            value = state.minDiameter,
                            onValueChange = onUpdateMinDiameter,
                            label = "Минимальный",
                            backgroundColor = colorResource(R.color.dark_background),
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        NumberEditor(
                            value = state.maxDiameter,
                            onValueChange = onUpdateMaxDiameter,
                            label = "Максимальный",
                            backgroundColor = colorResource(R.color.dark_background),
                        )
                    }
                }
                Spacer(Modifier.height(12.dp))
                SectionHeader(text = "Срок выполнения заказа")
                Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                    Box(modifier = Modifier.weight(1f)) {
                        NumberEditor(
                            value = state.minWorkPeriod,
                            onValueChange = onUpdateMinWorkPeriod,
                            label = "Минимальный",
                            backgroundColor = colorResource(R.color.dark_background)
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        NumberEditor(
                            value = state.maxWorkPeriod,
                            onValueChange = onUpdateMaxWorkPeriod,
                            label = "Максимальный",
                            backgroundColor = colorResource(R.color.dark_background)
                        )
                    }
                }
                Spacer(Modifier.height(18.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        FillingField(
                            value = state.newFilling,
                            onValueChange = onUpdateNewFilling,
                        )
                    }

                    FillingNew(
                        onClick = {
                            if (state.newFilling.isNotBlank()) {
                                onUpdateFillings(state.fillings + state.newFilling)
                                onUpdateNewFilling("")
                            }
                        },
                        modifier = Modifier.padding(8.dp, 0.dp, 0.dp, 0.dp)
                    )
                }


                Spacer(Modifier.height(18.dp))
                LazyRow(
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

            if (!(state.error == null || state.error == "")) {
                Text(
                    state.error,
                    style = TextStyles.regular(colorResource(R.color.dark_accent))
                )
            }
        }
    }
}

@Composable
fun CustomSettingsStatefulScreen(
    viewModel: CustomSettingsViewModel = hiltViewModel(),
    onSave: () -> Unit,
    topBar: @Composable () -> Unit
) {
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
        topBar = topBar,
        bottomBar = {
            ActiveButton(
                onClick = { viewModel.saveCustomSettings(onSave) },
                modifier = Modifier
                    .height(48.dp)
                    .fillMaxWidth()
                    .padding(4.dp)
            ) {
                Text(
                    "Добавить"
                )
            }
        }
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
        topBar = { TopNameBar("Кастомизация") {} },
        bottomBar = { ActiveButton(
            onClick = {  },
            modifier = Modifier
                .height(60.dp)
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Text(
                text = "Сохранить",
                style = TextStyles.button(colorResource(R.color.light_background)),
            )
        } }
    )
}