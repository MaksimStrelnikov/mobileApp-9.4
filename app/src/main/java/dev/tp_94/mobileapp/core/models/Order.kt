package dev.tp_94.mobileapp.core.models

import kotlinx.datetime.LocalDate

data class Order(
    val cake: Cake,
    val date: LocalDate,
    val orderStatus: OrderStatus,
    val price: Int,
)

enum class OrderStatus {
    PENDING_APPROVAL,
    PENDING_PAYMENT,
    IN_PROGRESS,
    DONE,
    RECEIVED,
    CANCELED,
    REJECTED
}