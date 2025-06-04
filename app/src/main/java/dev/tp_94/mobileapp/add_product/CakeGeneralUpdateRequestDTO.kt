package dev.tp_94.mobileapp.add_product

import com.google.gson.annotations.SerializedName

data class CakeGeneralUpdateRequestDTO(
    @SerializedName("image") val image: String,
    @SerializedName("confectioner_id") val confectionerId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String = "",
    @SerializedName("fillings") val fillings: List<String>,
    @SerializedName("required_time") val requiredTime: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("diameter") val diameter: Float,
    @SerializedName("weight") val weight: Float,
    @SerializedName("text") val text: String?,
    @SerializedName("text_size") val textSize: Int,
    @SerializedName("text_x") val textX: Float,
    @SerializedName("text_y") val textY: Float,
    @SerializedName("image_scale") val scale: Float = 1f,
)
