package dev.tp_94.mobileapp.payment.presentation.order

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.metrica.YandexMetrica
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.orders.domain.UpdateOrderStatusUseCase
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.payment.domain.GetAllCardsUseCase
import dev.tp_94.mobileapp.payment.domain.PayOrderUseCase
import dev.tp_94.mobileapp.payment.presentation.PaymentMethodsResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class OrderPaymentViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getAllCards: GetAllCardsUseCase,
    private val payOrderUseCase: PayOrderUseCase
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

    fun selectCard(card: Card?) {
        Log.println(Log.INFO, "Log", "Got in selected change $card")
        _state.value = _state.value.copy(selected = card)
        Log.println(Log.INFO, "Log", "Put in selected change ${_state.value.selected}")
    }

    init {
        getAllCards()
    }

    fun onPay(order: Order, onSuccessfulPay: () -> Unit, onErrorPay: () -> Unit) {
        viewModelScope.launch {
            val result = payOrderUseCase.execute(order, _state.value.selected!!)
            if (result is OrdersResult.Success) {
                YandexMetrica.reportEvent(
                    "order_payment",
                    "{\"screen\":\"order_payment\", \"action\":\"pay\", " +
                            "\"amount\":\"${state.value.order.price},}\""
                )
                onSuccessfulPay()
            } else {
                onErrorPay()
            }
        }
    }

    fun refreshCards() {
        getAllCards()
    }
}