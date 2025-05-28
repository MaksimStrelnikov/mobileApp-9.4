package dev.tp_94.mobileapp.basket.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

sealed class BasketResult {
    sealed class Success : BasketResult() {
        data class Basket(val basket: List<CakeGeneral>) : Success()
        data object Empty : Success()
    }

    data class Error(val message: String) : BasketResult()
}
