package dev.tp_94.mobileapp.add_product.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

sealed class ProductResult {
    data object Success : ProductResult()
    data class Error(val message: String) : ProductResult()
}