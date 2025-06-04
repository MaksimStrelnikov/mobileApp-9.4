package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.OrderStatus

data class OrderStatusPatchRequestDTO (
    @SerializedName("status") val orderStatus: OrderStatus
)

data class OrderPricePatchRequestDTO (
    @SerializedName("price") val price: Int,
)
