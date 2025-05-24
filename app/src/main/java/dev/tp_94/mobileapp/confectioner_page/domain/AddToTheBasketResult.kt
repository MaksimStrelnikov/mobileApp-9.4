package dev.tp_94.mobileapp.confectioner_page.domain

import dev.tp_94.mobileapp.core.models.CakeGeneral

sealed class AddToTheBasketResult {
    data class Success(val cake: CakeGeneral): AddToTheBasketResult()
    data class Error(val message: String): AddToTheBasketResult()
}