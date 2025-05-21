package dev.tp_94.mobileapp.order_payment.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.order_payment.presentation.PaymentMethodsResult
import javax.inject.Inject

class GetAllCardsUseCase @Inject constructor(private val sessionCache: SessionCache, private val repository: CardRepository) {
    suspend fun execute():PaymentMethodsResult {
        try {
            val result = repository.getAllCards(sessionCache.session!!.user)
            return PaymentMethodsResult.Success(result)
        } catch (e: Exception) {
            return PaymentMethodsResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}