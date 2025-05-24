package dev.tp_94.mobileapp.order_view.presentation

import dev.tp_94.mobileapp.core.models.Order
import java.lang.Error

data class OrderState(
    val order: Order,
    val dialogOpen: Boolean = false,
    val error: String? = null
)
