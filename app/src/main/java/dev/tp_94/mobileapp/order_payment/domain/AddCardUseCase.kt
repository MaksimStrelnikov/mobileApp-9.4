package dev.tp_94.mobileapp.order_payment.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.order_payment.presentation.NewCardAdditionResult
import dev.tp_94.mobileapp.order_payment.presentation.NewCardAdditionState
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardRepository: CardRepository, private val sessionCache: SessionCache) {
    suspend fun execute(value: NewCardAdditionState): NewCardAdditionResult {
        try {
            val result = cardRepository.addNewCard(value.number, value.expiration, value.cvcCode, sessionCache.session!!.user)
            return NewCardAdditionResult.Success(result)
        } catch (e: Exception) {
            return NewCardAdditionResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}
