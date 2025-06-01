package dev.tp_94.mobileapp.withdrawal.domain

import dev.tp_94.mobileapp.withdrawal.data.BalanceResponseDTO
import dev.tp_94.mobileapp.withdrawal.data.WithdrawRequestDTO

interface WithdrawalRepository {
    /**
     * Performs withdrawal
     */
    suspend fun withdrawal(withdrawRequestDTO: WithdrawRequestDTO): BalanceResponseDTO

    suspend fun getBalance(): BalanceResponseDTO
}
