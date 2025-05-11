package dev.tp_94.mobileapp.main_customer.domain

import dev.tp_94.mobileapp.core.models.Confectioner
import javax.inject.Inject

class GetConfectionersUseCase @Inject constructor() {
    fun execute(amount: Int): List<Confectioner> {
        return arrayListOf()
    }
}
