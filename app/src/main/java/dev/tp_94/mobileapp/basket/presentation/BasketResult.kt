package dev.tp_94.mobileapp.basket.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

sealed class BasketResult {
    data class Success(val basket: List<CakeGeneral>) : BasketResult()
    data class Error(val message: String) : BasketResult()
}
