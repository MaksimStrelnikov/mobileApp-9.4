package dev.tp_94.mobileapp.confectioner_page.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.confectioner_page.domain.AddToBasketUseCase
import dev.tp_94.mobileapp.confectioner_page.domain.GetProductsUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Restrictions
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.custom_order_settings.domain.GetRestrictionsUseCase
import dev.tp_94.mobileapp.custom_order_settings.presentation.RestrictionsResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class ConfectionerPageViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    private val addToBasketUseCase: AddToBasketUseCase,
    private val getRestrictionsUseCase: GetRestrictionsUseCase,
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {

    private val confectioner = savedStateHandle.get<String>("confectionerJson")
        ?.let { URLDecoder.decode(it, "UTF-8") }
        ?.let { Json.decodeFromString<Confectioner>(it) }
        ?: error("Confectioner not provided")

    private val _state = MutableStateFlow(
        ConfectionerPageState(
            confectioner = confectioner,
            restrictions = Restrictions(),
            products = emptyList()
        )
    )
    init {
        getRestrictions()
        getProducts()
    }

    val state = _state.asStateFlow()

    val session = sessionCache.session

    fun exit() {
        return sessionCache.clearSession()
    }

    fun addToBasket(cake: CakeGeneral) {
        viewModelScope.launch {
            val response = addToBasketUseCase.execute(
                    cake = cake,
                )
            //TODO or not: add error message
        }
    }

    private fun getRestrictions() {
        viewModelScope.launch {
            val result = getRestrictionsUseCase.execute(confectioner)
            if (result is RestrictionsResult.Success) {
                _state.value = _state.value.copy(restrictions = result.restrictions)
            }
            //TODO: add error handling
        }
    }

    private fun getProducts() {
        viewModelScope.launch {
            val result = getProductsUseCase.execute(confectioner)
            if (result is GetProductsResult.Success) {
                _state.value = _state.value.copy(products = result.cakes)
            }
            //TODO: add error handling
        }
    }
}