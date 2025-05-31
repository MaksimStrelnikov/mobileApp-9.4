package dev.tp_94.mobileapp.orders.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(): OrdersResult {
        try {
            if (sessionCache.session.value == null) return OrdersResult.Error("Пользователь не существует")
            val dtos = orderRepository.getAllOrders()
            val list = dtos.map {
                it.toOrder(
                    customer = it.customer.toCustomer(),
                    confectioner = it.confectioner.toConfectioner()
                )
            }
            return OrdersResult.Success.SuccessGet(list)
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
