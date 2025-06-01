package dev.tp_94.mobileapp.withdrawal.domain

import dev.tp_94.mobileapp.withdrawal.presentation.WithdrawalResult
import javax.inject.Inject

class GetBalanceUseCase @Inject constructor(private val repository: WithdrawalRepository) {
    suspend fun execute(): WithdrawalResult {
        try {
            val result = repository.getBalance()
            return WithdrawalResult.Success(result.canWithdraw, result.inProgress)
        } catch (e: Exception) {
            return WithdrawalResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
