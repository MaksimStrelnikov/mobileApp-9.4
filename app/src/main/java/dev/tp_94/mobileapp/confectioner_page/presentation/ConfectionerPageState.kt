package dev.tp_94.mobileapp.confectioner_page.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner

data class ConfectionerPageState(
    val confectioner: Confectioner,
    val products: List<CakeGeneral> = arrayListOf()
)
