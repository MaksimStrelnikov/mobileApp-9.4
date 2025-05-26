package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.withdrawal.domain.WithdrawalRepository

class WithdrawalRepositoryImpl: WithdrawalRepository {
    override suspend fun withdrawal(card: Card, sum: Int) {
        throw Exception("Не реализовано")
        //TODO: back awaiting
    }
}