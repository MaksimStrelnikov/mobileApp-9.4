package dev.tp_94.mobileapp.main_customer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.basket.presentation.BasketResult
import dev.tp_94.mobileapp.confectioner_page.domain.AddToBasketUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.main_customer.domain.GetCakesUseCase
import dev.tp_94.mobileapp.main_customer.domain.GetConfectionersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val getConfectionersUseCase: GetConfectionersUseCase,
    private val getCakesUseCase: GetCakesUseCase,
    private val addToBasketUseCase: AddToBasketUseCase
) : ViewModel() {
    //TODO: add product request in init
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    private fun getConfectioners(amount: Int) {
        viewModelScope.launch {
            val result = getConfectionersUseCase.execute(amount)
            if (result is MainResult.Success.Confectioners) {
                _state.value = _state.value.copy(confectioners = result.confectioners)
            } else if (result is MainResult.Error) {
                //TODO: add error handling
            }
        }
    }

    private fun getCakes(amount: Int) {
        viewModelScope.launch {
            val result = getCakesUseCase.execute(amount)
            if (result is MainResult.Success.Products) {
                _state.value = _state.value.copy(products = result.products)
            } else if (result is MainResult.Error) {
                //TODO: add error handling
            }
        }
    }

    init {
        getConfectioners(3)
        getCakes(8)
    }

    val session = sessionCache.session

    fun exit() {
        sessionCache.clearSession()
    }

    fun updateSearchText(it: String) {
        _state.value = _state.value.copy(search = it)
    }

    fun addToBasket(cake: CakeGeneral) {
        viewModelScope.launch {
            val response = addToBasketUseCase.execute(
                    cake = cake
                )
            if (response is BasketResult.Error) {
                //TODO or not: add error message
            }
        }
    }
}