package dev.tp_94.mobileapp.orders.presentation

import dev.tp_94.mobileapp.core.models.Order

sealed class OrdersResult {
    sealed class Success: OrdersResult() {
        data class SuccessGet(val orders: List<Order>): Success()
        data class SuccessUpdate(val order: Order): Success()
        data object SuccessAdd: Success()
    }
    data class Error(val message: String): OrdersResult()
}