package dev.tp_94.mobileapp.data

import dev.tp_94.mobileapp.core.api.WithdrawalApi
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.withdrawal.data.BalanceResponseDTO
import dev.tp_94.mobileapp.withdrawal.data.WithdrawRequestDTO
import dev.tp_94.mobileapp.withdrawal.domain.WithdrawalRepository

class WithdrawalRepositoryImpl(
    private val api: WithdrawalApi
): WithdrawalRepository  {
    override suspend fun withdrawal(withdrawRequestDTO: WithdrawRequestDTO): BalanceResponseDTO {
        val response = api.withdraw(withdrawRequestDTO)
        if (!response.isSuccessful) throw Exception(response.message())
        return response.body()!!
    }

    override suspend fun getBalance(): BalanceResponseDTO {
        val response = api.balance()
        if (!response.isSuccessful) throw Exception(response.message())
        return response.body()!!
    }
}