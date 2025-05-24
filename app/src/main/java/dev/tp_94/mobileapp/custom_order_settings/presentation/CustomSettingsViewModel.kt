package dev.tp_94.mobileapp.custom_order_settings.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.add_product.presentation.AddProductResult
import dev.tp_94.mobileapp.core.models.Restrictions
import dev.tp_94.mobileapp.custom_order_settings.domain.GetRestrictionsUseCase
import dev.tp_94.mobileapp.custom_order_settings.domain.UpdateRestrictionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomSettingsViewModel @Inject constructor(
    private val getRestrictionsUseCase: GetRestrictionsUseCase,
    private val updateRestrictionsUseCase: UpdateRestrictionsUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow(
        CustomSettingsState()
    )
    val state = _state.asStateFlow()

    fun updateCustomAcceptable(value: Boolean) = _state.value.copy(isCustomAcceptable = value)
        .also { _state.value = it }

    fun updateImageAcceptable(value: Boolean) = _state.value.copy(isImageAcceptable = value)
        .also { _state.value = it }

    fun updateShapeAcceptable(value: Boolean) = _state.value.copy(isShapeAcceptable = value)
        .also { _state.value = it }

    fun updateMinDiameter(value: String) = _state.value.copy(minDiameter = value)
        .also { _state.value = it }

    fun updateMaxDiameter(value: String) = _state.value.copy(maxDiameter = value)
        .also { _state.value = it }

    fun updateMinWorkPeriod(value: String) = _state.value.copy(minWorkPeriod = value)
        .also { _state.value = it }

    fun updateMaxWorkPeriod(value: String) = _state.value.copy(maxWorkPeriod = value)
        .also { _state.value = it }

    fun updateNewFilling(value: String) = _state.value.copy(newFilling = value)
        .also { _state.value = it }

    fun updateFillings(value: List<String>) = _state.value
        .copy(fillings = _state.value.fillings + value).also { _state.value = it }

    init {
        getRestrictions()
    }

    private fun getRestrictions() {
        viewModelScope.launch {
            val result = getRestrictionsUseCase.execute()
            if (result is RestrictionsResult.Success) {
                _state.value = _state.value.copy(
                    isCustomAcceptable = result.restrictions.isCustomAcceptable,
                    isImageAcceptable = result.restrictions.isImageAcceptable,
                    isShapeAcceptable = result.restrictions.isShapeAcceptable,
                    minDiameter = result.restrictions.minDiameter.toString(),
                    maxDiameter = result.restrictions.maxDiameter.toString(),
                    minWorkPeriod = result.restrictions.minPreparationDays.toString(),
                    maxWorkPeriod = result.restrictions.maxPreparationDays.toString(),
                    fillings = result.restrictions.fillings
                )
                _state.value = _state.value.copy(error = null)
            } else if (result is RestrictionsResult.Error) {
                _state.value = _state.value.copy(error = result.message)
            }
        }
    }

    fun saveCustomSettings() {
        viewModelScope.launch {
            val result = updateRestrictionsUseCase.execute(
                isCustomAcceptable = _state.value.isCustomAcceptable,
                isImageAcceptable = _state.value.isImageAcceptable,
                isShapeAcceptable = _state.value.isShapeAcceptable,
                minDiameter = _state.value.minDiameter.toFloat(),
                maxDiameter = _state.value.maxDiameter.toFloat(),
                minPreparationDays = _state.value.minWorkPeriod.toInt(),
                maxPreparationDays = _state.value.maxWorkPeriod.toInt(),
                fillings = _state.value.fillings,
            )
            if (result is RestrictionsResult.Success) {
                _state.value = _state.value.copy(error = null)
            } else if (result is RestrictionsResult.Error) {
                _state.value = _state.value.copy(error = result.message)
            }
        }
    }
}

