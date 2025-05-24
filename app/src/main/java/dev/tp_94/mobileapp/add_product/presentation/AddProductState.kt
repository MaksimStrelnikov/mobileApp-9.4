package dev.tp_94.mobileapp.add_product.presentation



data class AddProductState (
    val name: String = "",
    val description: String = "",
    val diameter: String = "",
    val weight: String = "",
    val workPeriod: String = "",
    val price: String = "",
    val image: String? = null,
    val isLoading: Boolean = false,
)