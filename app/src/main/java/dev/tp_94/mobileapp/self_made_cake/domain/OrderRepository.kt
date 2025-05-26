package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.orders.data.dto.OrderResponseDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderPatchRequestDTO

interface OrderRepository {
    suspend fun placeOrder(orderRequestDTO: OrderRequestDTO)
    suspend fun placeOrder(orderFullRequestDTO: OrderFullRequestDTO)
    suspend fun getAllOrders(): List<OrderResponseDTO>
    suspend fun updateOrderStatus(orderId: Long, request: OrderPatchRequestDTO): OrderResponseDTO
}