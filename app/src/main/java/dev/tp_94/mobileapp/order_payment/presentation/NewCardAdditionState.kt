package dev.tp_94.mobileapp.order_payment.presentation

data class NewCardAdditionState(
    val number: String = "",
    val expiration: String = "",
    val cvcCode: String = ""
)