package dev.tp_94.mobileapp.withdrawal.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Card
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.toConfectioner
import dev.tp_94.mobileapp.withdrawal.presentation.WithdrawalResult
import javax.inject.Inject

class WithdrawalUseCase @Inject constructor(
    private val repository: WithdrawalRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(
        card: Card, sum: Int
    ): WithdrawalResult {
        try {
            val response = repository.withdrawal(card, sum)
            sessionCache.saveSession(Session(response.toConfectioner(), accessToken = "token" /*TODO: TOKENIZATION*/))
            return WithdrawalResult.Success
        } catch (e: Exception) {
            return WithdrawalResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}
