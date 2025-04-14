package dev.tp_94.mobileapp.selfmadecake.presentation

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.models.Cake
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class SelfMadeCakeViewModel @Inject constructor() : ViewModel() {
    fun openColorPicker() {
        _state.value = _state.value.copy(colorPickerOpen = true)
    }

    fun closeColorPicker() {
        _state.value = _state.value.copy(colorPickerOpen = false)
    }

    fun setColor(color: Color) {
        _state.value = _state.value.copy(cake = _state.value.cake.copy(color = color))
    }

    fun setDiameter(diameter: Float) {
        _state.value = _state.value.copy(cake = _state.value.cake.copy(diameter = diameter))
    }

    private val _state = MutableStateFlow(ScreenState(
        colorPickerOpen = false,
        cake = Cake(Color.Cyan, 10f)
    ))
    val state = _state.asStateFlow()
}