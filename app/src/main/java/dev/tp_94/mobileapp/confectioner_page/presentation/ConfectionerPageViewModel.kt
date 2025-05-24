package dev.tp_94.mobileapp.confectioner_page.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.confectioner_page.domain.MoveToBasketUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.User
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
    private val moveToBasketUseCase: MoveToBasketUseCase) : ViewModel() {
    private val _state = MutableStateFlow(
        ConfectionerPageState(
            savedStateHandle.get<String>("confectionerJson")
                ?.let { URLDecoder.decode(it, "UTF-8") }
                ?.let { Json.decodeFromString<Confectioner>(it) }
                ?: error("Confectioner not provided")
        )
    )
    val state = _state.asStateFlow()

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun exit() {
        return sessionCache.clearSession()
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