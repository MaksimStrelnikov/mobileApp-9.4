package dev.tp_94.mobileapp.selfmadecake.domain

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.User

interface OrderRepository {
    suspend fun placeCustomCakeOrder(cakeCustom: CakeCustom, customer: Customer, confectioner: Confectioner)
    suspend fun getAllOrders(user: User?): List<Order>
    suspend fun updateOrderStatus(user: User?, order: Order, status: OrderStatus)
}