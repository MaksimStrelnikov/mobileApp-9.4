package dev.tp_94.mobileapp.order_payment.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.order_payment.domain.AddCardUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class OrderPaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    addCardUseCase: AddCardUseCase,
) : ViewModel() {
    private val _mainState = MutableStateFlow(
        OrderPaymentState(
            order = savedStateHandle.get<String>("order")
                ?.let { URLDecoder.decode(it, "UTF-8") }
                ?.let {
                    Json {
                        serializersModule = CakeSerializerModule.module
                    }.decodeFromString<Order>(it)
                }
                ?: error("Order not provided")
        )
    )
    val mainState = _mainState.asStateFlow()

    private lateinit var _newCardState: MutableStateFlow<NewCardAdditionState>
    val newCardState = _newCardState.asStateFlow()

    fun createNewCard() {
        _newCardState = MutableStateFlow(NewCardAdditionState())
    }

    fun addNewCard() {

    }
}