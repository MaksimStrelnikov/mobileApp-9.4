package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

data class CakeGeneralRequestDTO(
    @SerializedName("confectioner_id") val confectionerId: Long,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("diameter") val diameter: Float,
    @SerializedName("weight") val weight: Float,
    @SerializedName("required_time") val reqTime: Int,
    @SerializedName("price") val price: Int
)

fun CakeGeneralRequestDTO.toParts(): Map<String, RequestBody> {
    return mapOf(
        "confectioner_id" to confectionerId.toString().toRequestBody("text/plain".toMediaType()),
        "name" to name.toRequestBody("text/plain".toMediaType()),
        "description" to description.toRequestBody("text/plain".toMediaType()),
        "required_time" to reqTime.toString().toRequestBody("text/plain".toMediaType()),
        "diameter" to diameter.toString().toRequestBody("text/plain".toMediaType()),
        "weight" to weight.toString().toRequestBody("text/plain".toMediaType()),
        "price" to price.toString().toRequestBody("text/plain".toMediaType())
    )
}