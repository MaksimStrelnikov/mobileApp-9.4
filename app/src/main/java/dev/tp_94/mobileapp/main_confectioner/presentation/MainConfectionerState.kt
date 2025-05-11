package dev.tp_94.mobileapp.main_confectioner.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner

data class MainConfectionerState(
    val confectioner: Confectioner,
    val products: List<CakeGeneral> = arrayListOf(),
)
