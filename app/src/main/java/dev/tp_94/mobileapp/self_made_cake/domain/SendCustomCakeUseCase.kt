package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.self_made_cake.presentation.SelfMadeCakeResult
import javax.inject.Inject

class SendCustomCakeUseCase @Inject constructor(private val repository: OrderRepository) {
    //TODO: custom cake creation is 1 query, order is another
    suspend fun execute(cake: CakeCustom, customer: Customer, confectioner: Confectioner): SelfMadeCakeResult {
        try {
            repository.placeCustomCakeOrder(cake, customer, confectioner)
            return SelfMadeCakeResult.Success()
        } catch (e: Exception) {
            return SelfMadeCakeResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}