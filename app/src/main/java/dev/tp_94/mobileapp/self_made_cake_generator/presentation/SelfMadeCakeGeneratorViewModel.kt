package dev.tp_94.mobileapp.self_made_cake_generator.presentation

import android.net.Uri
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.R
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.self_made_cake.domain.SendCustomCakeUseCase
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class SelfMadeCakeGeneratorViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    private val sendCustomCakeUseCase: SendCustomCakeUseCase
) : ViewModel() {

    fun updateFillings(value: List<String>) = _state.value
        .copy(fillings = value).also { _state.value = it }

    fun setDiameter(diameter: Float) {
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(diameter = diameter))
    }

    fun updateComment(comment: String) {
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(description = comment))
    }

    fun updatePrompt(prompt: String) {
        _state.value =
            _state.value.copy(prompt = prompt)
    }

    //TODO: make it work
    fun generateImage() {
        _state.value =
            _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(imageUri = null))
    }

    fun sendCustomCake(onSuccess: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = sendCustomCakeUseCase.execute(_state.value.cakeCustom, (getUser() as Customer), _state.value.confectioner)
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

    private val _state = MutableStateFlow(
        SelfMadeCakeGeneratorState(
        cakeCustom = CakeCustom(Color.Unspecified, 10f),
        confectioner = savedStateHandle.get<String>("confectionerJson")
            ?.let { URLDecoder.decode(it, "UTF-8") }
            ?.let { Json.decodeFromString<Confectioner>(it) }
            ?: error("Confectioner not provided")
    ))
    val state = _state.asStateFlow()
}