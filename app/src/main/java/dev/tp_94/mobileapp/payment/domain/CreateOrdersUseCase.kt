package dev.tp_94.mobileapp.payment.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class CreateOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(cakes: Map<CakeGeneral, Int>): OrdersResult {
        try {
            if (sessionCache.session == null) return OrdersResult.Error("Вы не авторизованы")
            if (sessionCache.session!!.user !is Customer) return OrdersResult.Error("Недостаточно прав для формирования заказа")
            for (cake in cakes) {
                orderRepository.placeGeneralCakeOrder(
                    cakeGeneral = cake.key,
                    customer = sessionCache.session!!.user as Customer,
                    amount = cake.value
                )
            }
            return OrdersResult.Success.SuccessAdd
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
