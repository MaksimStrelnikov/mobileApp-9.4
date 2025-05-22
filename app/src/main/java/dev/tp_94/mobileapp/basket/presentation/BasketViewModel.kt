package dev.tp_94.mobileapp.basket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.basket.domain.ClearBasketUseCase
import dev.tp_94.mobileapp.basket.domain.GetBasketUseCase
import dev.tp_94.mobileapp.basket.domain.UpdateBasketUseCase
import dev.tp_94.mobileapp.core.models.CakeGeneral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    getBasketUseCase: GetBasketUseCase,
    private val updateBasketUseCase: UpdateBasketUseCase,
    private val clearBasketUseCase: ClearBasketUseCase
) : ViewModel() {
    fun add(it: CakeGeneral) {
        viewModelScope.launch {
            val result = updateBasketUseCase.execute(_state.value.items + it)
            if (result is BasketResult.Success) {
                _state.value = _state.value.copy(items = result.basket)
            } else {
                //TODO: add error messaging to user
            }
        }
    }

    fun remove(it: CakeGeneral) {
        viewModelScope.launch {
            val result = updateBasketUseCase.execute(_state.value.items - it)
            if (result is BasketResult.Success) {
                _state.value = _state.value.copy(items = result.basket)
            } else {
                //TODO: add error messaging to user
            }
        }
    }

    fun clearBasket() {
        viewModelScope.launch {
            val result = clearBasketUseCase.execute()
            if (result is BasketResult.Success) {
                _state.value = _state.value.copy(items = result.basket)
            } else {
                //TODO: add error messaging to user
            }
        }
    }

    private lateinit var _state: MutableStateFlow<BasketState>
    lateinit var state: StateFlow<BasketState>

    init {
        viewModelScope.launch {
            val result = getBasketUseCase.execute()
            _state = if (result is BasketResult.Success) {
                MutableStateFlow(BasketState(items = result.basket))
            } else {
                MutableStateFlow(BasketState())
                //TODO: add error messaging to user
            }
            state = _state.asStateFlow()
        }
    }


}
