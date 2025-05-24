package dev.tp_94.mobileapp.custom_order_settings.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.custom_order_settings.presentation.RestrictionsResult
import dev.tp_94.mobileapp.self_made_cake.domain.RestrictionRepository
import dev.tp_94.mobileapp.core.models.Restrictions
import javax.inject.Inject

class UpdateRestrictionsUseCase @Inject constructor(
    private val repository: RestrictionRepository,
    private val sessionCache: SessionCache
) {
    suspend fun execute(
        isCustomAcceptable: Boolean,
        isImageAcceptable: Boolean,
        isShapeAcceptable: Boolean,
        minDiameter: Float,
        maxDiameter: Float,
        minPreparationDays: Int,
        maxPreparationDays: Int,
        fillings: List<String>
    ): RestrictionsResult {
        try {
            if (sessionCache.session!!.user !is Confectioner)
                throw Exception("Данный пользователь не имеет достаточно прав, чтобы изменять данные на этом экране")

            if (isCustomAcceptable) {
                if (minDiameter < 0 || maxDiameter < 0)
                    return RestrictionsResult
                        .Error("Диаметр не может быть отрицательным")
                if (minDiameter > maxDiameter)
                    return RestrictionsResult
                        .Error("Минимальная диаметр не может быть больше максимального")
                if (minPreparationDays < 0 || maxPreparationDays < 0)
                    return RestrictionsResult
                        .Error("Количество дней не может быть отрицательным")
                if (minPreparationDays > maxPreparationDays)
                    return RestrictionsResult
                        .Error("Минимальное количество дней не может быть больше максимального")
            }

            val restrictions = Restrictions(
                isCustomAcceptable = isCustomAcceptable,
                isImageAcceptable = isImageAcceptable,
                isShapeAcceptable = isShapeAcceptable,
                minDiameter = minDiameter,
                maxDiameter = maxDiameter,
                minPreparationDays = minPreparationDays,
                maxPreparationDays = maxPreparationDays,
                fillings = fillings,
                confectioner = sessionCache.session!!.user as Confectioner
            )
            if (sessionCache.session == null || sessionCache.session!!.user !is Confectioner) {
                throw Exception("Данный пользователь не имеет достаточно прав, чтобы изменять данные на этом экране")
            }
            val result = repository.updateCustomCakeRestrictions(restrictions)
            return RestrictionsResult.Success(result)
        } catch (e: Exception) {
            return RestrictionsResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}