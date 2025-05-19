package dev.tp_94.mobileapp.withdrawal.domain

import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.withdrawal.presentation.WithdrawalResult
import javax.inject.Inject

class WithdrawalUseCase @Inject constructor(
    private val repository: WithdrawalRepository
) {
    suspend fun execute(
        card: Card, sum: Int
    ): WithdrawalResult {
        try {
            repository.withdrawal(card, sum)
            return WithdrawalResult.Success
        } catch (e: Exception) {
            return WithdrawalResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}
