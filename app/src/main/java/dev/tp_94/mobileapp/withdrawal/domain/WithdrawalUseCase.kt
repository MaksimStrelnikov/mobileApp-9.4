package dev.tp_94.mobileapp.withdrawal.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.toConfectioner
import dev.tp_94.mobileapp.withdrawal.data.WithdrawRequestDTO
import dev.tp_94.mobileapp.withdrawal.presentation.WithdrawalResult
import javax.inject.Inject

class WithdrawalUseCase @Inject constructor(
    private val repository: WithdrawalRepository
) {
    suspend fun execute(
        card: Card, sum: Int, canWithdraw: Int
    ): WithdrawalResult {
        if (sum > canWithdraw) return WithdrawalResult.Error("Недостаточно средств")
        if (sum <= 0) return WithdrawalResult.Error("Сумма должна быть больше 0")
        try {
            val result = repository.withdrawal(
                WithdrawRequestDTO(
                    amount = sum,
                    number = card.number,
                    expiration = card.expirationDate,
                    cvc = card.cvcCode
                )
            )
            return WithdrawalResult.Success(result.canWithdraw, result.inProgress)
        } catch (e: Exception) {
            return WithdrawalResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}
