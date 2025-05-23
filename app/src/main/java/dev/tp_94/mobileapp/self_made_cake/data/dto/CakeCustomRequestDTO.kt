package dev.tp_94.mobileapp.self_made_cake.data.dto

import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class CakeCustomRequestDTO(
    val confectionerId: Long,
    val name: String,
    val description: String,
    val fillings: List<String>,
    val reqTime: Int,
    val color: String,
    val diameter: Float,
    val text: String,
    val textSize: Int,
    val textX: Float,
    val textY: Float,
    val price: Int,
)

fun CakeCustomRequestDTO.toParts(): Map<String, RequestBody> {
    return mapOf(
        "confectioner_id" to confectionerId.toString().toRequestBody("text/plain".toMediaType()),
        "name" to name.toRequestBody("text/plain".toMediaType()),
        "description" to description.toRequestBody("text/plain".toMediaType()),
        "fillings" to fillings.joinToString(",").toRequestBody("text/plain".toMediaType()), // если API не принимает массив напрямую
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

