package dev.tp_94.mobileapp.basket.domain

import dev.tp_94.mobileapp.basket.presentation.BasketResult
import dev.tp_94.mobileapp.core.SessionCache
import javax.inject.Inject

class ClearBasketUseCase @Inject constructor(
    private val repository: BasketRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(): BasketResult {
        try {
            if (sessionCache.session.value == null) return BasketResult.Error("Вы не авторизованы")
            repository.clearBasket(sessionCache.session.value!!.user.phoneNumber)
            return BasketResult.Success.Basket(emptyList())
        } catch (e: Exception) {
            return BasketResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
