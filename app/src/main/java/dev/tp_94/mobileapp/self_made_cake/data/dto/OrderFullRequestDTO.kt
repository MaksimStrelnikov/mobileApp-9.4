package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName

data class OrderFullRequestDTO(
    @SerializedName("customer_id") val customerId: Int,
    @SerializedName("cake_id") val cakeId: Int,
    @SerializedName("price") val price: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("order_status") val orderStatus: String,
    @SerializedName("payment_status") val paymentStatus: String, val eta: String,
    @SerializedName("is_custom") val isCustom: Boolean,
    @SerializedName("created_at") val createdAt: String
)
