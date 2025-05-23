package dev.tp_94.mobileapp.core.models

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Order(
    val id: Long = 0,
    val cake: Cake,
    val date: LocalDate,
    val orderStatus: OrderStatus,
    val price: Int,
    val quantity: Int = 1,
    val customer: Customer,
    val confectioner: Confectioner
)

enum class OrderStatus {
    //backend-fronted statuses
    PENDING_APPROVAL,
    PENDING_PAYMENT,
    IN_PROGRESS,
    DONE,
    RECEIVED,
    CANCELED,
    REJECTED,
    //frontend-only statuses
}