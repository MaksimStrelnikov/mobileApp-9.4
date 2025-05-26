package dev.tp_94.mobileapp.core.models

import dev.tp_94.mobileapp.custom_order_settings.data.RestrictionsRequestDTO


data class Restrictions(
    val isCustomAcceptable: Boolean = false,
    val isImageAcceptable: Boolean = false,
    val isShapeAcceptable: Boolean = false,
    val minDiameter: Float = 0f,
    val maxDiameter: Float = 0f,
    val minPreparationDays: Int = 0,
    val maxPreparationDays: Int = 0,
    val fillings: List<String> = emptyList()
)

fun Restrictions.toDto(): RestrictionsRequestDTO {
    return RestrictionsRequestDTO(
        canMakeCustom = isCustomAcceptable,
        doImages = isImageAcceptable,
        doShapes = isShapeAcceptable,
        minDiameter = minDiameter,
        maxDiameter = maxDiameter,
        minEtaDays = minPreparationDays,
        maxEtaDays = maxPreparationDays,
        fillings = fillings
    )
}
