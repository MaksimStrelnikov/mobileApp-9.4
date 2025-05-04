package dev.tp_94.mobileapp.selfmadecake.domain


data class Restrictions(
    val minDiameter: Float,
    val maxDiameter: Float,
    val minPreparationDays: Int,
    val maxPreparationDays: Int,
    val fillings: List<String>
)
