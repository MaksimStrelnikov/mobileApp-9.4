package dev.tp_94.mobileapp.order_payment.presentation

import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Order

data class OrderPaymentState (
    val cards: List<Card> = listOf(),
    val selected: Card? = null,
    val order: Order,
)
