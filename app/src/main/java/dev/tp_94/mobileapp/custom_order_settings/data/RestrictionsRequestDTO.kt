package dev.tp_94.mobileapp.custom_order_settings.data

import com.google.gson.annotations.SerializedName

data class RestrictionsRequestDTO(
    @SerializedName("do_custom") val canMakeCustom: Boolean,
    @SerializedName("min_diameter") val minDiameter: Float,
    @SerializedName("max_diameter") val maxDiameter: Float,
    @SerializedName("min_eta_days") val minEtaDays: Int,
    @SerializedName("max_eta_days") val maxEtaDays: Int,
    @SerializedName("fillings") val fillings: List<String>,
    @SerializedName("do_images") val doImages: Boolean,
    @SerializedName("do_shapes") val doShapes: Boolean
)