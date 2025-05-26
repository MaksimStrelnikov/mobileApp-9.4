package dev.tp_94.mobileapp.custom_order_settings.data

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.Restrictions

data class RestrictionsResponseDTO(
    //TODO: check if canMakeCustom SerializedName is correct
    @SerializedName("do_custom") val canMakeCustom: Boolean,
    @SerializedName("min_diameter") val minDiameter: Float,
    @SerializedName("max_diameter") val maxDiameter: Float,
    @SerializedName("min_eta_days") val minEtaDays: Int,
    @SerializedName("max_eta_days") val maxEtaDays: Int,
    @SerializedName("fillings") val fillings: List<String>,
    @SerializedName("do_images") val doImages: Boolean,
    @SerializedName("do_shapes") val doShapes: Boolean
) {
    fun toRestrictions(): Restrictions = Restrictions(
        isCustomAcceptable = canMakeCustom,
        isImageAcceptable = doImages,
        isShapeAcceptable = doShapes,
        minDiameter = minDiameter,
        maxDiameter = maxDiameter,
        minPreparationDays = minEtaDays,
        maxPreparationDays = maxEtaDays,
        fillings = fillings
    )
}
