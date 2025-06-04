package dev.tp_94.mobileapp.self_made_cake.presentation

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.util.fastRoundToInt
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Restrictions
import dev.tp_94.mobileapp.custom_order_settings.domain.GetRestrictionsUseCase
import dev.tp_94.mobileapp.custom_order_settings.presentation.RestrictionsResult
import dev.tp_94.mobileapp.self_made_cake.domain.SendCustomCakeUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import java.util.Calendar
import java.util.Date
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SelfMadeCakeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    private val sendCustomCakeUseCase: SendCustomCakeUseCase,
    private val getRestrictionsUseCase: GetRestrictionsUseCase
) : ViewModel() {

    fun updateFillings(value: List<String>) =
        _state.value.copy(cakeCustom = _state.value.cakeCustom.copy(fillings = value))
            .also { _state.value = it }

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
            _state.value.copy(
                cakeCustom = _state.value.cakeCustom.copy(
                    diameter = diameter.fastRoundToInt().toFloat()
                )
            )
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

    fun updatePreparation(date: Date) {
        val nowCal = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val targetCal = Calendar.getInstance().apply {
            time = date
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val diffMillis = targetCal.timeInMillis - nowCal.timeInMillis

        _state.value = _state.value.copy(
            cakeCustom = _state.value.cakeCustom.copy(
                preparation = TimeUnit.MILLISECONDS.toDays(diffMillis).toInt()
            )
        )
    }

    fun sendCustomCake(onSuccess: () -> Unit) {
        _state.value = _state.value.copy(isLoading = true)
        viewModelScope.launch {
            val result = sendCustomCakeUseCase.execute(
                _state.value.cakeCustom,
                (session.value!!.user as Customer),
                _state.value.restrictions
            )
            if (result is SelfMadeCakeResult.Success) {
                onSuccess()
                _state.value = _state.value.copy(error = "")
            } else if (result is SelfMadeCakeResult.Error) {
                _state.value = _state.value.copy(error = result.message)
            }
            _state.value = _state.value.copy(isLoading = false)
        }
    }

    private fun getRestrictions() {
        viewModelScope.launch {
            val result = getRestrictionsUseCase.execute(confectioner)
            if (result is RestrictionsResult.Success) {
                _state.value = _state.value.copy(
                    restrictions = result.restrictions,
                    cakeCustom = _state.value.cakeCustom.copy(
                        diameter = result.restrictions.minDiameter.toFloat(),
                        preparation = result.restrictions.minPreparationDays
                    )
                )
            }
            //TODO: add error handling
        }
    }

    val session = sessionCache.session

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
            restrictions = Restrictions()
        )
    )
    val state = _state.asStateFlow()

    init {
        getRestrictions()
    }
}