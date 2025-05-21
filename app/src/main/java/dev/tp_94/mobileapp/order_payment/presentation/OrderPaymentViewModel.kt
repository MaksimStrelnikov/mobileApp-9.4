package dev.tp_94.mobileapp.order_payment.presentation

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
import dev.tp_94.mobileapp.order_payment.domain.GetAllCardsUseCase
import dev.tp_94.mobileapp.orders.domain.UpdateOrderStatusUseCase
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
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
    private val statusUseCase: UpdateOrderStatusUseCase
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

    private fun getAllCards() {
        lateinit var result: PaymentMethodsResult
        viewModelScope.launch {
            result = getAllCards.execute()
            if (result is PaymentMethodsResult.Success) {
                _mainState.value =
                    _mainState.value.copy(cards = (result as PaymentMethodsResult.Success).cards)
            }
        }
        //TODO: add error handling
    }

    fun changeStatus() {
        viewModelScope.launch {
            val result = statusUseCase.execute(mainState.value.order, OrderStatus.IN_PROGRESS, mainState.value.order.price)
            if (result is OrdersResult.Success) {
                initialization()
            }
            //TODO: error handling
        }
    }


    fun selectCard(card: Card?) {
        Log.println(Log.INFO, "Log", "Got in selected change $card")
        _mainState.value = _mainState.value.copy(selected = card)
        Log.println(Log.INFO, "Log", "Put in selected change ${_mainState.value.selected}")
    }

    fun initialization() {
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