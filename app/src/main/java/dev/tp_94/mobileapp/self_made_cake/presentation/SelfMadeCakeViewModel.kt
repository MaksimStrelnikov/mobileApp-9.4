package dev.tp_94.mobileapp.self_made_cake.presentation

import android.net.Uri
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.self_made_cake.domain.SendCustomCakeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class SelfMadeCakeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    private val sendCustomCakeUseCase: SendCustomCakeUseCase
) : ViewModel() {

    fun updateFillings(value: List<String>) = _state.value
        .copy(fillings = value).also { _state.value = it }

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
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(diameter = diameter))
    }

    fun updateText(text: String) {
        _state.value = _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(text = text))
    }

    fun updateTextOffset(textOffset: Offset) {
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(textOffset = textOffset))
    }

    fun updateImageOffset(imageOffset: Offset) {
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(imageOffset = imageOffset))
    }

    fun updateImage(imageUrl: String?) {
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(imageUrl = imageUrl.toString()))
    }

    fun updateComment(comment: String) {
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(description = comment))
    }

    fun setTextImageEditor(textImageEditor: Editor) {
        _state.value = _state.value.copy(textImageEditor = textImageEditor)
    }

    fun sendCustomCake(onSuccess: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = sendCustomCakeUseCase.execute(
                _state.value.cakeCustom,
                (getUser() as Customer),
                _state.value.confectioner
            )
            if (result is SelfMadeCakeResult.Success) {
                onSuccess()
                //TODO: add proper error handling
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun exit() {
        sessionCache.clearSession()
    }

    private val confectioner = savedStateHandle.get<String>("confectionerJson")
        ?.let { URLDecoder.decode(it, "UTF-8") }
        ?.let { Json.decodeFromString<Confectioner>(it) }
        ?: error("Confectioner not provided")

    private val _state = MutableStateFlow(
        SelfMadeCakeState(
            colorPickerOpen = false,
            cakeCustom = CakeCustom(
                Color.Cyan,
                10f,
                confectioner = confectioner
            ),
            confectioner = confectioner
        )
    )
    val state = _state.asStateFlow()
}