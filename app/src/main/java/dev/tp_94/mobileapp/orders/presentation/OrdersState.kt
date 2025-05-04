package dev.tp_94.mobileapp.orders.presentation

import dev.tp_94.mobileapp.core.models.Order

data class OrdersState(
    val orders: List<Order> = arrayListOf(),
    val dialogOpen: Boolean = false,
    val currentOrder: Order? = null
)
