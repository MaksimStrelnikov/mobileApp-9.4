package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.selfmadecake.domain.OrderRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockOrderRepository @Inject constructor(private val db: MockDB) : OrderRepository {
    override suspend fun placeCustomCakeOrder(
        cakeCustom: CakeCustom,
        customer: Customer,
        confectioner: Confectioner
    ) {
        delay(500)
        db.addNewOrder(
            cake = cakeCustom,
            customer = customer,
            confectioner = confectioner,
        )
    }

    override suspend fun getAllOrders(user: User?): List<Order> {
        return db.getAllOrders(user)
    }

    override suspend fun updateOrderStatus(user: User?, order: Order, status: OrderStatus) {
        db.updateOrderStatus(user, order, status)
    }

    override suspend fun updateOrderStatus(
        user: User?,
        order: Order,
        price: Int,
        status: OrderStatus
    ) {
        db.updateOrderStatus(user, order, price, status)
    }
}