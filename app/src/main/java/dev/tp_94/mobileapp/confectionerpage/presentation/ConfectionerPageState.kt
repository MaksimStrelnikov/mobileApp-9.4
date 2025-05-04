package dev.tp_94.mobileapp.confectionerpage.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner

data class ConfectionerPageState(
    val confectioner: Confectioner,
    val products: List<CakeGeneral> = arrayListOf()
)
