package dev.tp_94.mobileapp.selfmadecake.presentation

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.models.CakeCustom
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
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(color = color))
    }

    fun setDiameter(diameter: Float) {
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(diameter = diameter))
    }

    fun updateText(text: String) {
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(text = text))
    }

    fun updateTextOffset(textOffset: Offset) {
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(textOffset = textOffset))
    }

    fun updateImageOffset(imageOffset: Offset) {
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(imageOffset = imageOffset))
    }

    fun updateImage(imageUri: Uri?) {
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(imageUri = imageUri))
    }

    fun updateComment(comment: String) {
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(description = comment))
    }

    fun setTextImageEditor(textImageEditor: Editor) {
        _state.value = _state.value.copy(textImageEditor = textImageEditor)
    }

    private val _state = MutableStateFlow(SelfMadeCakeState(
        colorPickerOpen = false,
        cakeCustom = CakeCustom(Color.Cyan, 10f)
    ))
    val state = _state.asStateFlow()
}