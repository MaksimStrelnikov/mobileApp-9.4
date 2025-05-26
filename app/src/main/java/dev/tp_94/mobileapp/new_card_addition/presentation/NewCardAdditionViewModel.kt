package dev.tp_94.mobileapp.new_card_addition.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.new_card_addition.domain.AddCardUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewCardAdditionViewModel @Inject constructor(
    private val addCardUseCase: AddCardUseCase
): ViewModel() {
    private val _state = MutableStateFlow(NewCardAdditionState())
    val state = _state.asStateFlow()

    fun addNewCard(onSuccess: () -> Unit) {
        viewModelScope.launch {
            val result = addCardUseCase.execute(state.value)
            if (result is NewCardAdditionResult.Success) {
                _state.value = _state.value.copy(error = "")
                onSuccess()
            } else if (result is NewCardAdditionResult.Error) {
                _state.value = _state.value.copy(error = result.message)
            }
        }
    }

    fun changeNumber(number: String) {
        _state.value = _state.value.copy(number = number)
    }

    fun changeExpiration(expiration: String) {
        _state.value = _state.value.copy(expiration = expiration)
    }

    fun changeCvcCode(cvcCode: String) {
        _state.value = _state.value.copy(cvcCode = cvcCode)
    }
}