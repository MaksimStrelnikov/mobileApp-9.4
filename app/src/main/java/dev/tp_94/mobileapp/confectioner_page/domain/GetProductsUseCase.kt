package dev.tp_94.mobileapp.confectioner_page.domain

import dev.tp_94.mobileapp.confectioner_page.presentation.GetProductsResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: CakeRepository,
) {
    suspend fun execute(confectioner: Confectioner): GetProductsResult {
        return try {
            GetProductsResult.Success(
                repository.getAllByConfectioner(confectioner.id).map { it.toGeneral() }

            )
        } catch (e: Exception) {
            GetProductsResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}