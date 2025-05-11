package dev.tp_94.mobileapp.main_customer.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner

data class MainState(
    val search: String = "",
    val confectioners: List<Confectioner> = arrayListOf(),
    val products: List<CakeGeneral> = arrayListOf(),
)
