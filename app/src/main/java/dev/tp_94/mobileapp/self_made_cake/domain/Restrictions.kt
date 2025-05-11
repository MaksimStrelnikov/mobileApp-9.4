package dev.tp_94.mobileapp.self_made_cake.domain

import dev.tp_94.mobileapp.core.models.Confectioner


data class Restrictions(
    val minDiameter: Float,
    val maxDiameter: Float,
    val minPreparationDays: Int,
    val maxPreparationDays: Int,
    val fillings: List<String>,
    val confectioner: Confectioner
)
