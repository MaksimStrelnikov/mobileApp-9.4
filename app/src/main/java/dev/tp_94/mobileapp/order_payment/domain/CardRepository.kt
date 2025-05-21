package dev.tp_94.mobileapp.order_payment.domain

import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.User

interface CardRepository {
    suspend fun addNewCard(number: String, expiration: String, cvc: String, user: User): Card
    suspend fun getAllCards(user: User): List<Card>
}