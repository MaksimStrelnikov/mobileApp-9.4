package dev.tp_94.mobileapp.main_customer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.confectioner_page.domain.MoveToBasketUseCase
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
    private val moveToBasketUseCase: MoveToBasketUseCase
) : ViewModel() {
    //TODO: add product request in init
    private val _state = MutableStateFlow(MainState())
    val state = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(
            confectioners = _state.value.confectioners + getConfectionersUseCase.execute(5)
        )
    }

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun exit() {
        sessionCache.clearSession()
    }

    fun updateSearchText(it: String) {
        _state.value = _state.value.copy(search = it)
    }

    fun addToBasket(it: CakeGeneral) {
        viewModelScope.launch {
            val cake = state.value.products.firstOrNull { product ->
                product.id == it.id
            }
            if (cake != null) {
                val response = moveToBasketUseCase.execute(
                    cake = cake,
                    userPhone = getUser()?.phoneNumber ?: ""
                )
                //TODO or not: add error message
            }
        }
    }
}