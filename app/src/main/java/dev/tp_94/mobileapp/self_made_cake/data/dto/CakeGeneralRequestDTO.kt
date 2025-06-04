package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
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

fun CakeGeneralRequestDTO.toParts(): List<MultipartBody.Part> {
    return listOf(
        MultipartBody.Part.createFormData("confectioner_id", confectionerId.toString()),
        MultipartBody.Part.createFormData("name", name),
        MultipartBody.Part.createFormData("description", description),
        MultipartBody.Part.createFormData("required_time", reqTime.toString()),
        MultipartBody.Part.createFormData("diameter", diameter.toString()),
        MultipartBody.Part.createFormData("weight", weight.toString()),
        MultipartBody.Part.createFormData("price", price.toString())
    )
}
