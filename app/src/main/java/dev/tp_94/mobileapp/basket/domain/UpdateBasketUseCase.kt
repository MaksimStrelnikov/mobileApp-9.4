package dev.tp_94.mobileapp.basket.domain

import dev.tp_94.mobileapp.basket.presentation.BasketResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import javax.inject.Inject

class UpdateBasketUseCase @Inject constructor(
    private val repository: BasketRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(basket: List<CakeGeneral>): BasketResult {
        try {
            if (sessionCache.session == null) return BasketResult.Error("Вы не авторизованы")
            val result = repository.updateBasket(basket, sessionCache.session!!.user.phoneNumber)
            return BasketResult.Success(result)
        } catch (e: Exception) {
            return BasketResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
