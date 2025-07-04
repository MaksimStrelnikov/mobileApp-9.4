package dev.tp_94.mobileapp.custom_order_settings.domain

import dev.tp_94.mobileapp.core.SessionCache
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.custom_order_settings.presentation.RestrictionsResult
import dev.tp_94.mobileapp.self_made_cake.domain.RestrictionsRepository
import dev.tp_94.mobileapp.core.models.Restrictions
import dev.tp_94.mobileapp.core.models.toDto
import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsResponseDTO
import javax.inject.Inject

class UpdateRestrictionsUseCase @Inject constructor(
    private val repository: RestrictionsRepository,
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
            if (sessionCache.session.value == null || sessionCache.session.value!!.user !is Confectioner) {
                throw Exception("Данный пользователь не имеет достаточно прав, чтобы изменять данные на этом экране")
            }

            if (isCustomAcceptable) {
                if (minDiameter <= 0f || maxDiameter <= 0f)
                    return RestrictionsResult
                        .Error("Диаметр указан неверно")
                if (minDiameter > maxDiameter)
                    return RestrictionsResult
                        .Error("Минимальный диаметр не может быть больше максимального")
                if (minPreparationDays < 0 || maxPreparationDays < 0)
                    return RestrictionsResult
                        .Error("Количество дней не может быть отрицательным")
                if (minPreparationDays > maxPreparationDays)
                    return RestrictionsResult
                        .Error("Минимальное количество дней не может быть больше максимального")
                if (fillings.isEmpty())
                    return RestrictionsResult
                        .Error("Нужно добавить хотя бы один вид начинки")
            }

            val restrictions = Restrictions(
                isCustomAcceptable = isCustomAcceptable,
                isImageAcceptable = isImageAcceptable,
                isShapeAcceptable = isShapeAcceptable,
                minDiameter = minDiameter.toInt(),
                maxDiameter = maxDiameter.toInt(),
                minPreparationDays = minPreparationDays,
                maxPreparationDays = maxPreparationDays,
                fillings = fillings
            )
            val result = repository.updateCustomCakeRestrictions(
                restrictions.toDto()
            )
            return RestrictionsResult.Success(result.toRestrictions())
        } catch (e: Exception) {
            return RestrictionsResult.Error(e.message ?: "Возникла непредвиденная ошибка")
        }
    }
}