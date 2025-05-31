package dev.tp_94.mobileapp.add_product.domain

import dev.tp_94.mobileapp.add_product.presentation.ProductResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.login.domain.UserRepository
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import javax.inject.Inject

class DeleteProductUseCase @Inject constructor(
    private val sessionCache: SessionCache,
    private val repository: CakeRepository
) {
    suspend fun execute(cake: CakeGeneral): ProductResult {
        try {
            if (sessionCache.session.value == null || sessionCache.session.value!!.user !is Confectioner) return ProductResult.Error("Недостачно прав для удаления продукта")
            repository.deleteCake(cake.id)
            return ProductResult.Success
        } catch (e: Exception) {
            return ProductResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
