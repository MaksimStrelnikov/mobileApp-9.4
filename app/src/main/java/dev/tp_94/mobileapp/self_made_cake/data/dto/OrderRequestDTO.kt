package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName

data class OrderRequestDTO(
    @SerializedName("customer_id") val customerId: Long,
    @SerializedName("cake_id") val cakeId: Long,
    @SerializedName("price") val price: Int,
    @SerializedName("quantity") val quantity: Int
)
