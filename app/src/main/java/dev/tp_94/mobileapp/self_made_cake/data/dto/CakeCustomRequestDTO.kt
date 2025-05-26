package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class CakeCustomRequestDTO(
    @SerializedName("confectioner_id") val confectionerId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("fillings") val fillings: List<String>,
    @SerializedName("req_time") val reqTime: Int,
    @SerializedName("color") val color: String,
    @SerializedName("diameter") val diameter: Float,
    @SerializedName("text") val text: String,
    @SerializedName("text_size") val textSize: Int,
    @SerializedName("text_x") val textX: Float,
    @SerializedName("text_y") val textY: Float,
    @SerializedName("price") val price: Int,
)

fun CakeCustomRequestDTO.toParts(): Map<String, RequestBody> {
    return mapOf(
        "confectioner_id" to confectionerId.toString().toRequestBody("text/plain".toMediaType()),
        "name" to name.toRequestBody("text/plain".toMediaType()),
        "description" to description.toRequestBody("text/plain".toMediaType()),
        "fillings" to Gson().toJson(fillings).toRequestBody("application/json".toMediaType()), // если API не принимает массив напрямую
        "required_time" to reqTime.toString().toRequestBody("text/plain".toMediaType()),
        "color" to color.toRequestBody("text/plain".toMediaType()),
        "diameter" to diameter.toString().toRequestBody("text/plain".toMediaType()),
        "text" to text.toRequestBody("text/plain".toMediaType()),
        "text_size" to textSize.toString().toRequestBody("text/plain".toMediaType()),
        "text_x" to textX.toString().toRequestBody("text/plain".toMediaType()),
        "text_y" to textY.toString().toRequestBody("text/plain".toMediaType()),
        "price" to price.toString().toRequestBody("text/plain".toMediaType())
    )
}

