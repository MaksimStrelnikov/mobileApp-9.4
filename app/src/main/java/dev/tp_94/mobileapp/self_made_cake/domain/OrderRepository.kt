package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeCustomRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeGeneralRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderFullRequestDTO
import dev.tp_94.mobileapp.self_made_cake.data.dto.OrderRequestDTO

interface OrderRepository {
    suspend fun placeOrder(orderRequestDTO: OrderRequestDTO)
    suspend fun placeOrder(orderFullRequestDTO: OrderFullRequestDTO)
    //TODO: make return list of dto
    suspend fun getAllOrders(): List<Order>
    suspend fun updateOrderStatus(order: Order, price: Int, status: OrderStatus): Order
}