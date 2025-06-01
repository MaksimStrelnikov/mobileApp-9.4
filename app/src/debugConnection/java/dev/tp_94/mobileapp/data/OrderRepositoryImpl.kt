package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.data.HttpStatus.*
import dev.tp_94.mobileapp.orders.data.dto.OrderResponseDTO
import dev.tp_94.mobileapp.core.api.OrderApi
import dev.tp_94.mobileapp.payment.data.CardDTO
import dev.tp_94.mobileapp.payment.data.SeveralOrdersCreationPaymentRequestDTO
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

    override suspend fun getAllConfectionerOrders(): List<OrderResponseDTO> {
        val response = api.getAllConfectionerOrders()
        if (response.isSuccessful) {
            return response.body() ?: emptyList()
        } else if (response.code() == FORBIDDEN.status) {
            throw Exception("Недостаточно прав для просмотра заказов")
        } else if (response.code() == SERVER_ERROR.status) {
            throw Exception("Внутренняя ошибка сервера, повторите позднее")
        }
        throw Exception(response.message())
    }

    override suspend fun getAllCustomerOrders(): List<OrderResponseDTO> {
        val response = api.getAllCustomerOrders()
        if (response.isSuccessful) {
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

    override suspend fun placeAndPayOrders(severalOrdersCreationPaymentRequestDTO: SeveralOrdersCreationPaymentRequestDTO) {
        val response = api.createAndPayOrders(severalOrdersCreationPaymentRequestDTO)
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
    }



    override suspend fun payOrder(orderId: Long, cardDTO: CardDTO) {
        val response = api.payOrder(orderId, cardDTO)
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
    }

    override suspend fun receiveOrder(orderId: Long): OrderResponseDTO {
        val response = api.receiveOrder(orderId)
        if (!response.isSuccessful) {
            throw Exception(response.message())
        }
        return response.body()!!
    }

}