package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.self_made_cake.domain.OrderRepository
import kotlinx.coroutines.delay
import javax.inject.Inject

class MockOrderRepository @Inject constructor(private val db: MockDB, private val sessionCache: SessionCache) : OrderRepository {
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

    override suspend fun getAllOrders(): List<Order> {
        return db.getAllOrders(sessionCache.session?.user)
    }

    override suspend fun updateOrderStatus(
        order: Order,
        price: Int,
        status: OrderStatus
    ): Order {
        return db.updateOrderStatus(sessionCache.session?.user, order, price, status)
    }
}