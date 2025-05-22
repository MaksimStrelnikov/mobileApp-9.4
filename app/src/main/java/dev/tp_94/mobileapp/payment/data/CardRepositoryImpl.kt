package dev.tp_94.mobileapp.payment.data

import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.payment.domain.CardRepository
import javax.inject.Inject

class CardRepositoryImpl @Inject constructor(
    private val dao: CardDao
) : CardRepository {

    override suspend fun addNewCard(number: String, expiration: String, cvc: String, user: User): Card {
        val entity = CardEntity(userPhone = user.phoneNumber, number = number, expiration = expiration, cvc = cvc)
        if (dao.countCardByNumber(user.phoneNumber, number) != 0) {
            throw Exception("Карта уже привязана")
        }
        dao.insertCard(entity)
        return Card(number, expiration, cvc)
    }

    override suspend fun getAllCards(user: User): List<Card> {
        return dao.getCardsByUser(user.phoneNumber).map {
            Card(it.number, it.expiration, it.cvc)
        }
    }
}
