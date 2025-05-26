package dev.tp_94.mobileapp.self_made_cake.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.OrderStatus
import kotlinx.datetime.Instant

data class OrderFullRequestDTO(
    @SerializedName("customer_id") val customerId: Long,
    @SerializedName("cake_id") val cakeId: Long,
    @SerializedName("price") val price: Int,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("order_status") val orderStatus: OrderStatus,
    @SerializedName("is_custom") val isCustom: Boolean,
    @SerializedName("created_at") val createdAt: Instant
)
