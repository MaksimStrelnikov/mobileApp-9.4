package dev.tp_94.mobileapp.payment.presentation.order

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.payment.domain.GetAllCardsUseCase
import dev.tp_94.mobileapp.orders.domain.UpdateOrderStatusUseCase
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.payment.presentation.PaymentMethodsResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class OrderPaymentViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val savedStateHandle: SavedStateHandle,
    private val getAllCards: GetAllCardsUseCase,
    private val updateStatusUseCase: UpdateOrderStatusUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
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
    val state = _state.asStateFlow()

    private fun getAllCards() {
        lateinit var result: PaymentMethodsResult
        viewModelScope.launch {
            result = getAllCards.execute()
            if (result is PaymentMethodsResult.Success) {
                _state.value =
                    _state.value.copy(cards = (result as PaymentMethodsResult.Success).cards)
            }
        }
        //TODO: add error handling
    }

    private fun changeStatus() {
        viewModelScope.launch {
            val result = updateStatusUseCase.execute(
                    state.value.order,
                    OrderStatus.IN_PROGRESS,
                    state.value.order.price
                )
            if (result is OrdersResult.Success) {
                getAllCards()
            }
            //TODO: error handling
        }
    }


    fun selectCard(card: Card?) {
        Log.println(Log.INFO, "Log", "Got in selected change $card")
        _state.value = _state.value.copy(selected = card)
        Log.println(Log.INFO, "Log", "Put in selected change ${_state.value.selected}")
    }

    init {
        getAllCards()
    }

    fun onPay(card: Card, order: Order, onSuccessfulPay: () -> Unit, onErrorPay: () -> Unit) {
        //TODO: move mock payment for demonstrating screens to back-end
        if (Random.nextDouble() < 0.88) {
            changeStatus()
            onSuccessfulPay()
        } else {
            onErrorPay()
        }
    }
}