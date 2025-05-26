package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.orders.data.dto.OrderResponseDTO
import dev.tp_94.mobileapp.core.api.OrderApi
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderPatchRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val api: OrderApi
): OrderRepository {
    override suspend fun placeOrder(orderRequestDTO: OrderRequestDTO) {
        val response = api.createOrder(orderRequestDTO)
        if (response.code() != CREATED.status) {
            throw Exception(response.message())
        }
    }

    override suspend fun placeOrder(orderFullRequestDTO: OrderFullRequestDTO) {
        val response = api.createOrder(orderFullRequestDTO)
        if (response.code() != CREATED.status) {
            throw Exception(response.message())
        }
    }

    override suspend fun getAllOrders(): List<OrderResponseDTO> {
        val response = api.getAllOrders()
        if (response.code() == OK.status) {
            return response.body() ?: emptyList()
        } else if (response.code() == FORBIDDEN.status) {
            throw Exception("Недостаточно прав для просмотра заказов")
        } else if (response.code() == SERVER_ERROR.status) {
            throw Exception("Внутренняя ошибка сервера, повторите позднее")
        }
        throw Exception(response.message())
    }

    override suspend fun updateOrderStatus(orderId: Long, request: OrderPatchRequestDTO): OrderResponseDTO {
        val response = api.updateStatusOrder(orderId, request)
        if (!response.isSuccessful) throw Exception(response.message())
        return response.body()!!
    }

}