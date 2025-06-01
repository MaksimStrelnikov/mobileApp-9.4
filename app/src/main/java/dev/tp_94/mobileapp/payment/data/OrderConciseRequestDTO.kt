package dev.tp_94.mobileapp.payment.data

import com.google.gson.annotations.SerializedName

data class OrderConciseRequestDTO (
    @SerializedName("cake_id") val cakeId: Long,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("price") val price: Int
)
