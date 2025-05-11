package dev.tp_94.mobileapp.orders.domain

import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend fun execute(order: Order, status: OrderStatus, price: Int, user: User?): OrdersResult {
        //TODO: add user permission check
        return try{
            OrdersResult.Success.SuccessUpdate(repository.updateOrderStatus(user, order, price, status))
        } catch (e: Exception) {
            OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
