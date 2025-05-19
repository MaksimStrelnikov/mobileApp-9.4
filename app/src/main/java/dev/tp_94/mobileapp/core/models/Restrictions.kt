package dev.tp_94.mobileapp.core.models


data class Restrictions(
    val isCustomAcceptable: Boolean = false,
    val isImageAcceptable: Boolean = false,
    val isShapeAcceptable: Boolean = false,
    val minDiameter: Float,
    val maxDiameter: Float,
    val minPreparationDays: Int,
    val maxPreparationDays: Int,
    val fillings: List<String>,
    val confectioner: Confectioner
)
