package dev.tp_94.mobileapp.confectioner_page.domain

import dev.tp_94.mobileapp.confectioner_page.presentation.GetProductsResult
import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.self_made_cake.domain.CakeRepository
import javax.inject.Inject

class GetProductsUseCase @Inject constructor(
    private val repository: CakeRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(confectioner: Confectioner): GetProductsResult {
        try {
            if (sessionCache.session.value == null) {
                return GetProductsResult.Error("Данный пользователь не имеет достаточно прав, чтобы изменять данные на этом экране")
            }
            return GetProductsResult.Success(
                repository.getAllByConfectioner(confectioner.id).map { it.toGeneral() }

            )
        } catch (e: Exception) {
            return GetProductsResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}