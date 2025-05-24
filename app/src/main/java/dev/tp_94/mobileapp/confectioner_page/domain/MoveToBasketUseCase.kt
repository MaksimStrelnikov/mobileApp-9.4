package dev.tp_94.mobileapp.confectioner_page.domain

import dev.tp_94.mobileapp.basket.domain.BasketRepository
import dev.tp_94.mobileapp.core.models.CakeGeneral
import javax.inject.Inject

class MoveToBasketUseCase @Inject constructor(
    private val repository: BasketRepository,
) {
    suspend fun execute(
        cake: CakeGeneral,
        userPhone: String
    ): AddToTheBasketResult {
        if (userPhone == "") return AddToTheBasketResult.Error("Вы не авторизованы")
        try {
            //TODO: just check me
            repository.addToBasket(cake, userPhone)
            return AddToTheBasketResult.Success(cake)
        } catch (e: Exception) {
            return AddToTheBasketResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }

}