package dev.tp_94.mobileapp.add_product.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral


data class AddProductState (
    val cakeGeneral: CakeGeneral,
    val isLoading: Boolean = false,
    val error: String? = null
)