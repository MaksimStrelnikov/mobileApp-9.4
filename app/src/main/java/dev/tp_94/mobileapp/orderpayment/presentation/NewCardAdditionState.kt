package dev.tp_94.mobileapp.orderpayment.presentation

data class NewCardAdditionState(
    val number: String,
    val expiration: String,
    val cvcCode: String
)