package dev.tp_94.mobileapp.withdrawal.domain

import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.login.data.dto.UserResponseWithTokensDTO

interface WithdrawalRepository {
    /**
     * Performs withdrawal and updates current confectioner in session cache.
     */
    suspend fun withdrawal(card: Card, sum: Int): UserResponseWithTokensDTO
}
