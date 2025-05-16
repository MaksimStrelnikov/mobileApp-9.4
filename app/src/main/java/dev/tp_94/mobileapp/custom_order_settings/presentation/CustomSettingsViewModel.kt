package dev.tp_94.mobileapp.custom_order_settings.presentation

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class CustomSettingsViewModel @Inject constructor() : ViewModel() {
    private val _state = MutableStateFlow(
        CustomSettingsState()
    )
    val state = _state.asStateFlow()

    fun updateCustomAcceptable(value: Boolean) = _state.value.copy(isCustomAcceptable = value).also { _state.value = it }
    fun updateImageAcceptable(value: Boolean) = _state.value.copy(isImageAcceptable = value).also { _state.value = it }
    fun updateShapeAcceptable(value: Boolean) = _state.value.copy(isShapeAcceptable = value).also { _state.value = it }
    fun updateMinDiameter(value: String) = _state.value.copy(minDiameter = value).also { _state.value = it }
    fun updateMaxDiameter(value: String) = _state.value.copy(maxDiameter = value).also { _state.value = it }
    fun updateMinWorkPeriod(value: String) = _state.value.copy(minWorkPeriod = value).also { _state.value = it }
    fun updateMaxWorkPeriod(value: String) = _state.value.copy(maxWorkPeriod = value).also { _state.value = it }
    fun updateFillings(value: List<String>) = _state.value.copy(fillings = _state.value.fillings + value).also { _state.value = it }

}
