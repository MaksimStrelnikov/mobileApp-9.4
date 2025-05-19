package dev.tp_94.mobileapp.custom_order_settings.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.custom_order_settings.presentation.RestrictionsResult
import dev.tp_94.mobileapp.self_made_cake.domain.RestrictionRepository
import javax.inject.Inject

class GetRestrictionsUseCase @Inject constructor(
    private val repository: RestrictionRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(): RestrictionsResult {
        try {
            if (sessionCache.session == null || sessionCache.session!!.user !is Confectioner) {
                return RestrictionsResult.Error("Данный пользователь не имеет достаточно прав, чтобы изменять данные на этом экране")
            }
            val result = repository.getCustomCakeRestrictions(sessionCache.session!!.user as Confectioner)
            return RestrictionsResult.Success(result)
        } catch (e: Exception) {
            return RestrictionsResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}
