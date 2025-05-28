package dev.tp_94.mobileapp.basket.domain

import dev.tp_94.mobileapp.basket.presentation.BasketResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Customer
import javax.inject.Inject

class RemoveFromBasketUseCase @Inject constructor(
    private val repository: BasketRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(cake: CakeGeneral): BasketResult {
        if (sessionCache.session == null || sessionCache.session!!.user !is Customer) return BasketResult.Error("Недостаточно прав для добавления в корзину")
        val userPhone = (sessionCache.session!!.user as Customer).phoneNumber
        try {
            repository.removeFromBasket(cake, userPhone)
            return BasketResult.Success.Empty
        } catch (e: Exception) {
            return BasketResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}