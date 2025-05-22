package dev.tp_94.mobileapp.basket.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

data class BasketState(
    val items: List<CakeGeneral> = emptyList()
)
