package dev.tp_94.mobileapp.payment.domain

import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.payment.data.CardDTO
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class PayOrderUseCase @Inject constructor(
    private val repository: OrderRepository
) {
    suspend fun execute(order: Order, card: Card): OrdersResult {
        try {
            repository.payOrder(
                orderId = order.id,
                cardDTO = CardDTO(
                    number = card.number,
                    expiration = card.expirationDate,
                    cvc = card.cvcCode
                ),
            )
            return OrdersResult.Success.SuccessAdd
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
