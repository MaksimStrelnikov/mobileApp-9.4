package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.orders.data.OrderResponseDTO
import dev.tp_94.mobileapp.orders.data.toOrder
import dev.tp_94.mobileapp.self_made_cake.data.OrderApi
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi
): OrderRepository {
    override suspend fun placeOrder(orderRequestDTO: OrderRequestDTO) {
        val response = api.createOrder(orderRequestDTO)
        if (response.code() != CREATED.status) {
            throw Exception("Возникла непредвиденная ошибка")
        }
    }

    override suspend fun placeOrder(orderFullRequestDTO: OrderFullRequestDTO) {
        val response = api.createOrder(orderFullRequestDTO)
        if (response.code() != CREATED.status) {
            throw Exception("Возникла непредвиденная ошибка")
        }
    }

    override suspend fun getAllOrders(session: Session): List<OrderResponseDTO> {
        val response = api.getAllOrders()
        if (response.code() == OK.status) {
            return response.body() ?: emptyList()
        } else if (response.code() == UNAUTHORIZED.status || response.code() == FORBIDDEN.status) {
            throw Exception("Недостаточно прав для просмотра заказов")
        }
        throw Exception("Неизвестная ошибка, повторите позднее")
    }

    override suspend fun updateOrderStatus(order: Order, price: Int, status: OrderStatus): Order {
        TODO("Not yet implemented")
    }
}