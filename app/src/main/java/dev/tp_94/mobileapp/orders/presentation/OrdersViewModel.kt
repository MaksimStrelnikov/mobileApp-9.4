package dev.tp_94.mobileapp.orders.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.orders.domain.GetAllOrdersUseCase
import dev.tp_94.mobileapp.orders.domain.ReceiveOrderUseCase
import dev.tp_94.mobileapp.orders.domain.UpdateOrderStatusUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrdersViewModel @Inject constructor(
    private val sessionCache: SessionCache,
    private val getAllOrdersUseCase: GetAllOrdersUseCase,
    private val updateOrderStatusUseCase: UpdateOrderStatusUseCase,
    private val receiveOrderUseCase: ReceiveOrderUseCase
) : ViewModel() {

    val session = sessionCache.session

    fun exit() {
        sessionCache.clearSession()
    }

    fun getOrders() {
        viewModelScope.launch {
            val result = getAllOrdersUseCase.execute()
            if (result is OrdersResult.Success.SuccessGet) {
                _state.value = _state.value.copy(orders = result.orders)
            }
        }
    }

    fun changeStatus(price: Int, status: OrderStatus) {
        viewModelScope.launch {
            val result = updateOrderStatusUseCase.execute(state.value.currentOrder!!, status, price)
            if (result is OrdersResult.Success.SuccessUpdate) {
                _state.value =
                    _state.value.copy(orders = _state.value.orders.filter { it != state.value.currentOrder!! } + arrayListOf(
                        result.order
                    ))
            }
            //TODO: add error handling
        }
    }


    fun changeStatus(order: Order, status: OrderStatus) {
        if (status == OrderStatus.RECEIVED) {
            viewModelScope.launch {
                val result = receiveOrderUseCase.execute(order)
                if (result is OrdersResult.Success.SuccessUpdate) {
                    _state.value =
                        _state.value.copy(orders = _state.value.orders.filter { it != order } + arrayListOf(
                            result.order
                        ))
                } else if (result is OrdersResult.Error) {
                    //TODO: add error handling
                }
            }
        } else {
            viewModelScope.launch {
                val result = updateOrderStatusUseCase.execute(order, status, order.price)
                if (result is OrdersResult.Success.SuccessUpdate) {
                    _state.value =
                        _state.value.copy(orders = _state.value.orders.filter { it != order } + arrayListOf(
                            result.order
                        ))
                }
                //TODO: add error handling
            }
        }
    }

    fun onDialogOpen(order: Order) {
        _state.value = _state.value.copy(dialogOpen = true, currentOrder = order)
    }

    fun onDialogClose() {
        _state.value = _state.value.copy(dialogOpen = false, currentOrder = null)
    }

    private val _state = MutableStateFlow(OrdersState())
    val state = _state.asStateFlow()
}