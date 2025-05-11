package dev.tp_94.mobileapp.order_view.presentation

import dev.tp_94.mobileapp.core.models.Order

data class OrderState(
    val order: Order,
    val dialogOpen: Boolean = false,
)
