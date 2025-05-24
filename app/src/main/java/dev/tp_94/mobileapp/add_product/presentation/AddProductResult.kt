package dev.tp_94.mobileapp.add_product.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral

sealed class AddProductResult {
        data class Success(val cakeGeneral: CakeGeneral) : AddProductResult()
        data class Error(val message: String) : AddProductResult()
}