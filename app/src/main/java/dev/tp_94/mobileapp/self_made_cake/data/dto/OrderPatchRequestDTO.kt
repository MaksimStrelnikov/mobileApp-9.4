package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.OrderStatus

data class OrderPatchRequestDTO (
    @SerializedName("price") val price: Int,
    @SerializedName("status") val orderStatus: OrderStatus
)
