package dev.tp_94.mobileapp.withdrawal.presentation

import dev.tp_94.mobileapp.core.models.Card

data class WithdrawalState(
    val available: Int = 0,
    val sum: Int = 0,
    val inProcess: Int = 0,
    val cards: List<Card> = emptyList(),
    val selected: Card? = null
)
