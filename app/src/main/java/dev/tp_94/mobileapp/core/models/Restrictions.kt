package dev.tp_94.mobileapp.core.models

import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsRequestDTO


data class Restrictions(
    val isCustomAcceptable: Boolean = false,
    val isImageAcceptable: Boolean = false,
    val isShapeAcceptable: Boolean = false,
    val minDiameter: Int = 0,
    val maxDiameter: Int = 0,
    val minPreparationDays: Int = 0,
    val maxPreparationDays: Int = 0,
    val fillings: List<String> = emptyList()
)

fun Restrictions.toDto(): RestrictionsRequestDTO {
    return RestrictionsRequestDTO(
        canMakeCustom = isCustomAcceptable,
        doImages = isImageAcceptable,
        doShapes = isShapeAcceptable,
        minDiameter = minDiameter.toFloat(),
        maxDiameter = maxDiameter.toFloat(),
        minEtaDays = minPreparationDays,
        maxEtaDays = maxPreparationDays,
        fillings = fillings
    )
}
