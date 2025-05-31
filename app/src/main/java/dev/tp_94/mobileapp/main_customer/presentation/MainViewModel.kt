package dev.tp_94.mobileapp.main_customer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.confectioner_page.domain.AddToBasketUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.main_customer.domain.GetConfectionersUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    getConfectionersUseCase: GetConfectionersUseCase,
    private val addToBasketUseCase: AddToBasketUseCase
) : ViewModel() {
    //TODO: add product request in init
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            confectioners = _state.value.confectioners + getConfectionersUseCase.execute(5)
        )
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
            //TODO or not: add error message
        }
    }
}