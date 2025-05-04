package dev.tp_94.mobileapp.selfmadecake.domain

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.selfmadecake.presentation.SelfMadeCakeResult
import javax.inject.Inject

class SendCustomCakeUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend fun execute(cake: CakeCustom, customer: Customer, confectioner: Confectioner): SelfMadeCakeResult {
        try {
            repository.placeCustomCakeOrder(cake, customer, confectioner)
            return SelfMadeCakeResult.Success()
        } catch (e: Exception) {
            return SelfMadeCakeResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}