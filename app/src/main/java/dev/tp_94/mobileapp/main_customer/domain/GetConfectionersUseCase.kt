package dev.tp_94.mobileapp.main_customer.domain

import dev.tp_94.mobileapp.customers_feed.domain.ConfectionerRepository
import dev.tp_94.mobileapp.main_customer.presentation.MainResult
import javax.inject.Inject

class GetConfectionersUseCase @Inject constructor(
    private val repository : ConfectionerRepository
) {
    suspend fun execute(amount: Int): MainResult {
        if (amount <= 0) return MainResult.Error("Неверное количество кондитеров")
        try {
            //TODO: force backend to add new api for getting specific amount of confectioners
            val result = repository.getAll()
            val safeAmount = amount.coerceIn(0, result.size)
            return MainResult.Success.Confectioners(result.subList(0, safeAmount).map { it.toConfectioner() })
        } catch (e: Exception) {
            return MainResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
