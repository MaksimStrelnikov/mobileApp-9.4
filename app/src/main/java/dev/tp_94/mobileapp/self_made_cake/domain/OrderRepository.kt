package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.orders.data.OrderResponseDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO

interface OrderRepository {
    suspend fun placeOrder(orderRequestDTO: OrderRequestDTO)
    suspend fun placeOrder(orderFullRequestDTO: OrderFullRequestDTO)
    suspend fun getAllOrders(session: Session): List<OrderResponseDTO>

    //TODO: make valid and consistent
    suspend fun updateOrderStatus(order: Order, price: Int, status: OrderStatus): Order
}