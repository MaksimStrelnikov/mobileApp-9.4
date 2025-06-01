package dev.tp_94.mobileapp.payment.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.orders.presentation.OrdersResult
import dev.tp_94.mobileapp.payment.data.OrderConciseRequestDTO
import dev.tp_94.mobileapp.payment.data.SeveralOrdersCreationPaymentRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class CreateOrdersUseCase @Inject constructor(
    private val orderRepository: OrderRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(cakes: Map<CakeGeneral, Int>, card: Card): OrdersResult {
        try {
            if (sessionCache.session.value == null) return OrdersResult.Error("Вы не авторизованы")
            if (sessionCache.session.value!!.user !is Customer) return OrdersResult.Error("Недостаточно прав для формирования заказа")
            orderRepository.placeAndPayOrders(
                severalOrdersCreationPaymentRequestDTO = SeveralOrdersCreationPaymentRequestDTO(
                    orders = cakes.map { (cake, quantity) ->
                        OrderConciseRequestDTO(
                            cakeId = cake.id,
                            quantity = quantity,
                            price = cake.price * quantity
                        )
                    },
                    cardNumber = card.number,
                    expirationDate = card.expirationDate,
                    cvc = card.cvcCode
                )
            )
            return OrdersResult.Success.SuccessAdd
        } catch (e: Exception) {
            return OrdersResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
