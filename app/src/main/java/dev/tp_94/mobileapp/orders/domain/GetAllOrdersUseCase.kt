package dev.tp_94.mobileapp.orders.domain

import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend fun execute(user: User?): OrdersResult {
        try {
            if (user == null) return OrdersResult.Error("Пользователь не существует")
            return OrdersResult.Success.SuccessGet(repository.getAllOrders())
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
