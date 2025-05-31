package dev.tp_94.mobileapp.confectioner_page.domain

import dev.tp_94.mobileapp.basket.domain.BasketRepository
import dev.tp_94.mobileapp.basket.presentation.BasketResult
import dev.tp_94.mobileapp.confectioner_page.presentation.AddToTheBasketResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Customer
import javax.inject.Inject

class AddToBasketUseCase @Inject constructor(
    private val repository: BasketRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(cake: CakeGeneral): BasketResult {
        if (sessionCache.session.value == null || sessionCache.session.value!!.user !is Customer) return BasketResult.Error("Недостаточно прав для добавления в корзину")
        val userPhone = (sessionCache.session.value!!.user as Customer).phoneNumber
        try {
            repository.addToBasket(cake, userPhone)
            return BasketResult.Success.Empty
        } catch (e: Exception) {
            return BasketResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}