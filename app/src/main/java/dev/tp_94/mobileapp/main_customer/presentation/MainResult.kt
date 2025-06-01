package dev.tp_94.mobileapp.main_customer.presentation

import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner

sealed class MainResult {
    sealed class Success : MainResult() {
        data class Confectioners(val confectioners: List<Confectioner>) : Success()
        data class Products(val products: List<CakeGeneral>) : Success()
    }
    data class Error(val message: String) : MainResult()
}
