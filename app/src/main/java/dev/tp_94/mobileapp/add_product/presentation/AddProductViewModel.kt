package dev.tp_94.mobileapp.add_product.presentation

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.core.net.toUri
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.self_made_cake.domain.SendCustomCakeUseCase
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel  @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    private val sendCustomCakeUseCase: SendCustomCakeUseCase
) : ViewModel() {

    fun updateName(name: String) {
        _state.value = _state.value.copy(name = name)
    }

    fun updateDescription(description: String) {
        _state.value = _state.value.copy(description = description)
    }

    fun updateDiameter(diameter: String) {
        _state.value = _state.value.copy(diameter = diameter)
    }

    fun updateWeight(weight: String) {
        _state.value = _state.value.copy(weight = weight)
    }

    fun updateWorkPeriod(workPeriod: String) {
        _state.value = _state.value.copy(workPeriod = workPeriod)
    }

    fun updatePrice(price: String) {
        _state.value = _state.value.copy(price = price)
    }

    fun updateImage(image: String?) {
        _state.value = _state.value.copy(image = image)
    }

    //TODO: make this functions
    fun cancel() {

    }

    fun delete() {

    }

    fun save(onSave: () -> Unit) {

    }

    private val _state = MutableStateFlow(
        AddProductState(
            name = "Торт",
            description = "Это точно торт",
            diameter = "22",
            weight = "2",
            workPeriod = "2",
            price = "12000",
            image = null
    ))
    val state = _state.asStateFlow()
}