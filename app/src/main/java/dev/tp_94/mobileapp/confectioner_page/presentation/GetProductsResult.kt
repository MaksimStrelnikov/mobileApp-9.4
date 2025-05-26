package dev.tp_94.mobileapp.confectioner_page.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

sealed class GetProductsResult {
    data class Success(val cakes: List<CakeGeneral>): GetProductsResult()
    data class Error(val message: String): GetProductsResult()
}