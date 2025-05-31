package dev.tp_94.mobileapp.main_confectioner.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.confectioner_page.domain.GetProductsUseCase
import dev.tp_94.mobileapp.confectioner_page.presentation.GetProductsResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class MainConfectionerViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val getProductsUseCase: GetProductsUseCase,
) :
    ViewModel() {

    val session = sessionCache.session

    fun exit() {
        sessionCache.clearSession()
    }

    private val _state = MutableStateFlow(
        MainConfectionerState(
            confectioner = session.value!!.user as Confectioner,
            products = emptyList()
        )
    )

    init {
        viewModelScope.launch {
            sessionCache.session.collect { session ->
                val user = session?.user as? Confectioner
                if (user != null) {
                    _state.update { it.copy(confectioner = user) }
                }
            }
        }
        getProducts()
    }

    private fun getProducts() {
        viewModelScope.launch {
            val result = getProductsUseCase.execute(_state.value.confectioner)
            if (result is GetProductsResult.Success) {
                _state.value = _state.value.copy(products = result.cakes)
            }
            //TODO: add error handling
        }
    }

    val state = _state.asStateFlow()
}