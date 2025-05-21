package dev.tp_94.mobileapp.payment.presentation.basket

import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Order

data class BasketPaymentState (
    val cards: List<Card> = listOf(),
    val selected: Card? = null,
    val cakes: Map<CakeGeneral, Int>,
)
