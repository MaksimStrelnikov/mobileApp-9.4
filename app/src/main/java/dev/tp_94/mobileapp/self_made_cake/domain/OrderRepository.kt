package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.orders.data.dto.OrderResponseDTO
import dev.tp_94.mobileapp.payment.data.CardDTO
import dev.tp_94.mobileapp.payment.data.SeveralOrdersCreationPaymentRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderPricePatchRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderStatusPatchRequestDTO

interface OrderRepository {
    suspend fun placeOrder(orderRequestDTO: OrderRequestDTO)
    suspend fun placeOrder(orderFullRequestDTO: OrderFullRequestDTO)
    suspend fun getAllConfectionerOrders(): List<OrderResponseDTO>
    suspend fun getAllCustomerOrders(): List<OrderResponseDTO>
    suspend fun updateOrderStatus(orderId: Long, request: OrderStatusPatchRequestDTO): OrderResponseDTO
    suspend fun updateOrderPrice(orderId: Long, request: OrderPricePatchRequestDTO): OrderResponseDTO
    suspend fun placeAndPayOrders(severalOrdersCreationPaymentRequestDTO: SeveralOrdersCreationPaymentRequestDTO)
    suspend fun payOrder(orderId: Long, cardDTO: CardDTO)
    suspend fun receiveOrder(orderId: Long): OrderResponseDTO
}