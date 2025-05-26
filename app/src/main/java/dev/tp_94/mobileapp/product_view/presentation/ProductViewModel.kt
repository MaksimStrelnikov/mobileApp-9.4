package dev.tp_94.mobileapp.product_view.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.confectioner_page.domain.AddToBasketUseCase
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    private val addToBasketUseCase: AddToBasketUseCase
) : ViewModel() {
    private val json = Json { serializersModule = CakeSerializerModule.module }


    private val _state = MutableStateFlow(
        ProductState(
            cake = savedStateHandle.get<String>("cake")
                ?.let { URLDecoder.decode(it, "UTF-8") }
                ?.let { json.decodeFromString<CakeGeneral>(it) }
                ?: error("Cake not provided")
        )
    )
    val state = _state.asStateFlow()

    fun getUser(): User? {
        return sessionCache.session?.user
    }

    fun addToBasket(cake: CakeGeneral) {
        viewModelScope.launch {
            val response = addToBasketUseCase.execute(
                cake = cake,
                userPhone = getUser()?.phoneNumber ?: ""
            )
            //TODO or not: add error message
        }
    }

    fun exit() {
        sessionCache.clearSession()
    }
}