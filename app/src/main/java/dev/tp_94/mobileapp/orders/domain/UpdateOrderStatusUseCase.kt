package dev.tp_94.mobileapp.orders.domain

import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderPricePatchRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderStatusPatchRequestDTO
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class UpdateOrderStatusUseCase @Inject constructor(private val repository: OrderRepository) {
    suspend fun execute(order: Order, status: OrderStatus, price: Int): OrdersResult {
        return try {
            if (price != order.price) {
                repository.updateOrderPrice(
                    orderId = order.id,
                    request = OrderPricePatchRequestDTO(price = price)
                )
            }

            val dto = repository.updateOrderStatus(
                orderId = order.id,
                request = OrderStatusPatchRequestDTO(orderStatus = status)
            )

            OrdersResult.Success.SuccessUpdate(dto.toOrder())
        } catch (e: Exception) {
            OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
