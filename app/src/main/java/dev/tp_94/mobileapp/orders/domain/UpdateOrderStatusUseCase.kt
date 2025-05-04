package dev.tp_94.mobileapp.orders.domain

import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.selfmadecake.domain.OrderRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend fun execute(order: Order, status: OrderStatus, user: User?): OrdersResult {
        //TODO: add user permission check
        try{
            repository.updateOrderStatus(user, order, status)
            return OrdersResult.Success.SuccessUpdate("Успех")
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

    suspend fun execute(order: Order, status: OrderStatus, price: Int, user: User?): OrdersResult {
        //TODO: add user permission check
        try{
            repository.updateOrderStatus(user, order, price, status)
            return OrdersResult.Success.SuccessUpdate("Успех")
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
