package dev.tp_94.mobileapp.orders.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class ReceiveOrderUseCase @Inject constructor(private val orderRepository: OrderRepository, private val sessionCache: SessionCache) {
    suspend fun execute(order: Order): OrdersResult {
        if (sessionCache.session.value == null || sessionCache.session.value!!.user !is Customer) return OrdersResult.Error("Недостаточно прав для получения заказа")
        try {
            val result = orderRepository.receiveOrder(order.id)
            return OrdersResult.Success.SuccessUpdate(result.toOrder())
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}