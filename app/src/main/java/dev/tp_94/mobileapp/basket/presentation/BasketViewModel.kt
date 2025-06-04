package dev.tp_94.mobileapp.basket.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.basket.domain.ClearBasketUseCase
import dev.tp_94.mobileapp.basket.domain.GetBasketUseCase
import dev.tp_94.mobileapp.basket.domain.RemoveFromBasketUseCase
import dev.tp_94.mobileapp.confectioner_page.domain.AddToBasketUseCase
import dev.tp_94.mobileapp.core.models.CakeGeneral
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BasketViewModel @Inject constructor(
    private val getBasketUseCase: GetBasketUseCase,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val removeFromBasketUseCase: RemoveFromBasketUseCase,
    private val clearBasketUseCase: ClearBasketUseCase
) : ViewModel() {
    fun add(it: CakeGeneral) {
        viewModelScope.launch {
            val result = addToBasketUseCase.execute(it)
            if (result is BasketResult.Success) {
                getBasket()
            } else {
                //TODO: add error messaging to user
            }
        }
    }

    fun remove(it: CakeGeneral) {
        viewModelScope.launch {
            val result = removeFromBasketUseCase.execute(it)
            if (result is BasketResult.Success) {
                getBasket()
            } else {
                //TODO: add error messaging to user
            }
        }
    }

    fun clearBasket() {
        viewModelScope.launch {
            val result = clearBasketUseCase.execute()
            if (result is BasketResult.Success) {
                getBasket()
            } else {
                //TODO: add error messaging to user
            }
        }
    }

    private fun getBasket() {
        viewModelScope.launch {
            val result = getBasketUseCase.execute()
            _state.value = _state.value.copy(items = if (result is BasketResult.Success.Basket) {
                result.basket
            } else {
                emptyList()
                //TODO: add error messaging to user
            })
        }
    }

    fun update() {
        getBasket()
    }

    private val _state = MutableStateFlow(BasketState())
    val state = _state.asStateFlow()

    init {
        getBasket()
    }
}
