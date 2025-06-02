package dev.tp_94.mobileapp.custom_order_settings.domain

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.custom_order_settings.presentation.RestrictionsResult
import dev.tp_94.mobileapp.self_made_cake.domain.RestrictionsRepository
import javax.inject.Inject

class GetRestrictionsUseCase @Inject constructor(
    private val repository: RestrictionsRepository,
) {
    suspend fun execute(confectioner: Confectioner): RestrictionsResult {
        try {
            val dto = repository.getCustomCakeRestrictions(confectioner.id)
            return RestrictionsResult.Success(dto.toRestrictions())
        } catch (e: Exception) {
            return RestrictionsResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
