package dev.tp_94.mobileapp.new_card_addition.presentation

data class NewCardAdditionState(
    val number: String = "",
    val expiration: String = "",
    val cvcCode: String = "",
    val error: String? = null
)