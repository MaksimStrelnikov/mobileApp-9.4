package dev.tp_94.mobileapp.add_product.presentation

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
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
import dev.tp_94.mobileapp.add_product.presentation.components.CakeImageAddition
import dev.tp_94.mobileapp.core.themes.DescriptionEditor
import dev.tp_94.mobileapp.core.themes.PriceEditor
import dev.tp_94.mobileapp.core.themes.ActiveButton
import dev.tp_94.mobileapp.core.themes.DiscardButton
import dev.tp_94.mobileapp.core.themes.TextButton
import dev.tp_94.mobileapp.core.themes.TextStyles
import dev.tp_94.mobileapp.core.themes.TopNameBar
import dev.tp_94.mobileapp.core.themes.NameEditor
import dev.tp_94.mobileapp.core.themes.NumberEditor
import dev.tp_94.mobileapp.core.themes.WeightEditor


@Composable
fun AddProductStatefulScreen(
    viewModel: AddProductViewModel = hiltViewModel(),
    onError: () -> Unit,
    onSave: () -> Unit,
    topBar: @Composable () -> Unit
) {
    //TODO: rewrite onError logic
    val state = viewModel.state.collectAsStateWithLifecycle()
    AddProductStatelessScreen(
        state.value,
        onNameChange = { viewModel.updateName(it) },
        onDescriptionChange = { viewModel.updateDescription(it) },
        onDiameterChange = { viewModel.updateDiameter(it) },
        onWeightChange = { viewModel.updateWeight(it) },
        onWorkPeriodChange = { viewModel.updateWorkPeriod(it) },
        onPriceChange = { viewModel.updatePrice(it) },
        onImageChange = { viewModel.updateImage(it) },
        onCancellation = { viewModel.cancel() },
        onDelete = { viewModel.delete() },
        onSave = {
            viewModel.save(onSave)
        },
        topBar = topBar
    )
}


@Composable
fun AddProductStatelessScreen(
    state: AddProductState,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDiameterChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onWorkPeriodChange: (String) -> Unit,
    onPriceChange: (String) -> Unit,
    onImageChange: (Uri?) -> Unit,
    onSave: () -> Unit,
    onCancellation: () -> Unit,
    onDelete: () -> Unit,
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

            CakeImageAddition (
                onAdd = onImageChange,
                imageUri = state.image
            )
            Spacer(Modifier.height(16.dp))
            NameEditor(
                onChange = { onNameChange(it) },
                text = state.name,
                defaultText = "Название",
                backgroundColor = colorResource(R.color.dark_background)
            )
            Spacer(Modifier.height(8.dp))
            DescriptionEditor(
                onChange = { onDescriptionChange(it) },
                text = state.description,
                defaultText = "Описание",
                backgroundColor = colorResource(R.color.dark_background),
                modifier = Modifier.height(108.dp),
            )
            Spacer(Modifier.height(8.dp))
            NumberEditor(
                onValueChange = { onDiameterChange(it) },
                value = state.diameter,
                label = "Диаметр изделия (см)",
                backgroundColor = colorResource(R.color.dark_background),
                necessary = true,
            )
            Spacer(Modifier.height(8.dp))
            WeightEditor(
                onChange = { onWeightChange(it) },
                text = state.weight,
                backgroundColor = colorResource(R.color.dark_background),
            )
            Spacer(Modifier.height(8.dp))
            NumberEditor(
                onValueChange = { onWorkPeriodChange(it) },
                value = state.workPeriod,
                label = "Время работы (дни)",
                backgroundColor = colorResource(R.color.dark_background),
                necessary = true,
            )
            Spacer(Modifier.height(8.dp))
            PriceEditor(
                onChange = { onPriceChange(it) },
                text = state.price,
                backgroundColor = colorResource(R.color.dark_background),
            )
            Spacer(Modifier.height(16.dp))
            ActiveButton(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(
                    if (!state.isLoading) "Добавить" else "Добавляем..."
                )
            }
            Spacer(Modifier.height(8.dp))
            DiscardButton(
                onClick = onCancellation,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                shape = RoundedCornerShape(12.dp)
            )
            {
                Text(
                    "Отмена"
                )
            }
            Spacer(Modifier.height(32.dp))
            TextButton(
                onClick = onDelete,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 8.dp)
            ) {
                Text(
                    "Удалить товар",
                    style = TextStyles.button(color = colorResource(R.color.light_text))
                )
            }
        }
    }
}



@Preview
@Composable
fun PreviewAddProductStatelessScreen() {
    val state = remember {
        mutableStateOf(
            AddProductState(

            )
        )
    }

    MaterialTheme {
        AddProductStatelessScreen(
            state = state.value,
            onNameChange = { state.value = state.value.copy(name = it) },
            onDescriptionChange = { state.value = state.value.copy(description = it) },
            onDiameterChange = { state.value = state.value.copy(diameter = it) },
            onWeightChange = { state.value = state.value.copy(weight = it) },
            onWorkPeriodChange = { state.value = state.value.copy(workPeriod = it) },
            onPriceChange = { state.value = state.value.copy(price = it) },
            onImageChange = { state.value = state.value.copy(image = it) },
            onSave = {},
            onCancellation = {},
            onDelete = {},
            topBar = { TopNameBar("Добавить товар") { } }
        )
    }

}