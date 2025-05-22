package dev.tp_94.mobileapp.add_product.presentation

import android.net.Uri

data class AddProductState (
    val name: String = "",
    val description: String = "",
    val diameter: String = "",
    val weight: String = "",
    val workPeriod: String = "",
    val price: String = "",
    val image: Uri? = null,
    val isLoading: Boolean = false,
)