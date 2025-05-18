package dev.tp_94.mobileapp.product_view.presentation

import dev.tp_94.mobileapp.core.models.Cake
import dev.tp_94.mobileapp.core.models.Confectioner

data class ProductState (
    val cake: Cake,
    val price: Int,
    //TODO: ? val confectioner: Confectioner
)