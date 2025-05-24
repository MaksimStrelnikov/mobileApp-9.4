package dev.tp_94.mobileapp.orders.data

import dev.tp_94.mobileapp.core.models.Order

data class OrderResponseDTO(
    val id: Long,
    val customerId: Long,
    val confectionerId: Long,

)

fun OrderResponseDTO.toOrder(): Order {
    return Order(
        id = this.id,
        cake = TODO(),
        date = TODO(),
        orderStatus = TODO(),
        price = TODO(),
        quantity = TODO(),
        customer = TODO(),
        confectioner = TODO()
    )
}