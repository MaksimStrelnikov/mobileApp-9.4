package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName

data class CakeGeneralRequestDTO(
    @SerializedName("confectioner_id") val confectionerId: Int,
    @SerializedName("name") val name: String,
    @SerializedName("description") val description: String,
    @SerializedName("diameter") val diameter: Double,
    @SerializedName("weight") val weight: Double,
    @SerializedName("required_time") val reqTime: Int,
    @SerializedName("price") val price: Int
)