package dev.tp_94.mobileapp.order_view.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeSerializerModule
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.orders.domain.ReceiveOrderUseCase
import dev.tp_94.mobileapp.orders.domain.UpdateOrderStatusUseCase
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.net.URLDecoder
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val sessionCache: SessionCache,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase,
    private val receiveOrderUseCase: ReceiveOrderUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(
        OrderState(
            savedStateHandle.get<String>("order")
                ?.let { URLDecoder.decode(it, "UTF-8") }
                ?.let { Json { serializersModule = CakeSerializerModule.module }.decodeFromString<Order>(it) }
                ?: error("Order not provided")
        )
    )
    val state = _state.asStateFlow()

    val session = sessionCache.session

    fun exit() {
        sessionCache.clearSession()
    }

    fun changeStatus(price: Int, status: OrderStatus) {
        viewModelScope.launch {
            val result = updateOrderStatusUseCase.execute(state.value.order, status, price)
            if (result is OrdersResult.Success.SuccessUpdate) {
                _state.value = _state.value.copy(order = result.order, error = "")
            } else if (result is OrdersResult.Error) {
                _state.value = _state.value.copy(error = result.message)
            }
        }
    }


    fun changeStatus(status: OrderStatus) {
        if (status == OrderStatus.RECEIVED)  {
            viewModelScope.launch {
                val result = receiveOrderUseCase.execute(state.value.order)
                if (result is OrdersResult.Success.SuccessUpdate) {
                    _state.value = _state.value.copy(order = result.order, error = "")
                } else if (result is OrdersResult.Error) {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        } else {
            viewModelScope.launch {
                val result = updateOrderStatusUseCase.execute(state.value.order, status, state.value.order.price)
                if (result is OrdersResult.Success.SuccessUpdate) {
                    _state.value = _state.value.copy(order = result.order, error = "")
                } else if (result is OrdersResult.Error) {
                    _state.value = _state.value.copy(error = result.message)
                }
            }
        }
    }

    fun onDialogOpen() {
        _state.value = _state.value.copy(dialogOpen = true)
    }

    fun onDialogClose() {
        _state.value = _state.value.copy(dialogOpen = false)
    }
}
