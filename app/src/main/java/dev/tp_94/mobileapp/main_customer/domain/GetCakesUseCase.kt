package dev.tp_94.mobileapp.main_customer.domain

import dev.tp_94.mobileapp.main_customer.presentation.MainResult
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import javax.inject.Inject

class GetCakesUseCase @Inject constructor(private val repository: CakeRepository) {
    suspend fun execute(amount: Int): MainResult {
        if (amount <= 0) return MainResult.Error("Неверное количество продуктов")
        try {
            //TODO: force backend to add new api for getting specific amount of products
            val result = repository.getAllGeneral()
            val safeAmount = amount.coerceIn(0, result.size)
            return MainResult.Success.Products(result.subList(0, safeAmount).map { it.toGeneral() })
        } catch (e: Exception) {
            return MainResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
