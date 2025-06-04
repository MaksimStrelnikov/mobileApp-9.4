package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody

data class CakeCustomRequestDTO(
    @SerializedName("confectioner_id") val confectionerId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("fillings") val fillings: List<String>,
    @SerializedName("req_time") val reqTime: Int,
    @SerializedName("color") val color: String,
    @SerializedName("diameter") val diameter: Float,
    @SerializedName("image_path") val imagePath: String = "",
    @SerializedName("text") val text: String,
    @SerializedName("text_size") val textSize: Int,
    @SerializedName("text_x") val textX: Float,
    @SerializedName("text_y") val textY: Float,
    @SerializedName("price") val price: Int,
)


fun CakeCustomRequestDTO.toParts(): List<MultipartBody.Part> {

    val parts = mutableListOf<MultipartBody.Part>()

    parts += MultipartBody.Part.createFormData("confectioner_id", confectionerId.toString())
    parts += MultipartBody.Part.createFormData("name", name)
    parts += MultipartBody.Part.createFormData("description", description)
    parts += MultipartBody.Part.createFormData("required_time", reqTime.toString())
    parts += MultipartBody.Part.createFormData("color", color)
    parts += MultipartBody.Part.createFormData("diameter", diameter.toString())
    parts += MultipartBody.Part.createFormData("image_path", imagePath)
    parts += MultipartBody.Part.createFormData("text", text)
    parts += MultipartBody.Part.createFormData("text_size", textSize.toString())
    parts += MultipartBody.Part.createFormData("text_x", textX.toString())
    parts += MultipartBody.Part.createFormData("text_y", textY.toString())
    parts += MultipartBody.Part.createFormData("price", price.toString())

    fillings.forEach { filling ->
        parts += MultipartBody.Part.createFormData("fillings", filling)
    }

    return parts
}




