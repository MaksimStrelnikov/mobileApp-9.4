package dev.tp_94.mobileapp.new_card_addition.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.new_card_addition.presentation.NewCardAdditionResult
import dev.tp_94.mobileapp.new_card_addition.presentation.NewCardAdditionState
import dev.tp_94.mobileapp.payment.domain.CardRepository
import javax.inject.Inject

class AddCardUseCase @Inject constructor(private val cardRepository: CardRepository, private val sessionCache: SessionCache) {
    suspend fun execute(value: NewCardAdditionState): NewCardAdditionResult {
        try {
            if (value.number.length < 16) {
                NewCardAdditionResult.Error("Номер карты должен состоять из 16 цифр")
            }
            if (value.expiration.length < 4) {
                NewCardAdditionResult.Error("Срок действия карты должен состоять из 4 цифр")
            }
            if (value.cvcCode.length < 3) {
                NewCardAdditionResult.Error("CVC-код должен состоять из 3 цифр")
            }
            val result = cardRepository.addNewCard(value.number, value.expiration, value.cvcCode, sessionCache.session!!.user)
            return NewCardAdditionResult.Success(result)
        } catch (e: Exception) {
            return NewCardAdditionResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}
