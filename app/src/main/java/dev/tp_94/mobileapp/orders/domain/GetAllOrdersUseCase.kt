package dev.tp_94.mobileapp.orders.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class GetAllOrdersUseCase @Inject constructor(
    private val repository: OrderRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(): OrdersResult {
        try {
            if (sessionCache.session == null) return OrdersResult.Error("Пользователь не существует")
            val dtos = repository.getAllOrders(sessionCache.session!!)

            return OrdersResult.Success.SuccessGet(dtos.map { it.toOrder() })
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
