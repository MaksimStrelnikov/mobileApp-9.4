package dev.tp_94.mobileapp.custom_order_settings.data

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.Restrictions

data class RestrictionsResponseDTO(
    @SerializedName("confectioner_id") val confectioner_id: Int?,
    @SerializedName("min_diameter") val minDiameter: Double,
    @SerializedName("max_diameter") val maxDiameter: Double,
    @SerializedName("min_eta_days") val minEtaDays: Int,
    @SerializedName("max_eta_days") val maxEtaDays: Int,
    @SerializedName("fillings") val fillings: List<String>?,
    @SerializedName("do_images") val doImages: Boolean,
    @SerializedName("do_shapes") val doShapes: Boolean,
    @SerializedName("do_custom") val canMakeCustom: Boolean,
) {
    fun toRestrictions(): Restrictions = Restrictions(
        isCustomAcceptable = canMakeCustom,
        isImageAcceptable = doImages,
        isShapeAcceptable = doShapes,
        minDiameter = minDiameter.toFloat(),
        maxDiameter = maxDiameter.toFloat(),
        minPreparationDays = minEtaDays,
        maxPreparationDays = maxEtaDays,
        fillings = fillings?: emptyList()
    )
}
