package dev.tp_94.mobileapp.self_made_cake.data.dto

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.data.RetrofitInstance
import dev.tp_94.mobileapp.core.models.CakeCustom
import dev.tp_94.mobileapp.core.models.CakeGeneral
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO

data class CakeResponseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("confectioner") val confectioner: ConfectionerResponseDTO,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String?,
    @SerializedName("fillings") val fillings: List<String>,
    @SerializedName("required_time") val requiredTime: Int,
    @SerializedName("color") val color: Int,
    @SerializedName("image_url") val imageUrl: String?,
    @SerializedName("price") val price: Int,
    @SerializedName("diameter") val diameter: Float,
    @SerializedName("weight") val weight: Float,
    @SerializedName("text") val text: String?,
    @SerializedName("text_size") val textSize: Int,
    @SerializedName("text_x") val textX: Float,
    @SerializedName("text_y") val textY: Float,
    @SerializedName("is_custom") val isCustom: Boolean
) {
    fun toCustom(): CakeCustom = CakeCustom(
        name = name,
        color = Color(color),
        diameter = diameter,
        text = text ?: "",
        textOffset = Offset(textX, textY),
        imageUrl = imageUrl?.let {RetrofitInstance.IMAGES_URL + it},
        imageOffset = Offset(0f, 0f),
        fillings = fillings,
        preparation = requiredTime,
        description = description ?: "",
        confectioner = confectioner.toConfectioner()
    )


    fun toGeneral(): CakeGeneral = CakeGeneral(
        id = id,
        price = price,
        imageUrl = imageUrl?.let {RetrofitInstance.IMAGES_URL + it},
        name = name,
        description = description!!,
        diameter = diameter,
        weight = weight,
        preparation = requiredTime,
        confectioner = confectioner.toConfectioner()
    )

}