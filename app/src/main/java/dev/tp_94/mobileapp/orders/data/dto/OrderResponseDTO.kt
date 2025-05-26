package dev.tp_94.mobileapp.orders.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Order
import dev.tp_94.mobileapp.core.models.OrderStatus
import dev.tp_94.mobileapp.self_made_cake.data.dto.CakeResponseDTO
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

data class OrderResponseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("customer") val customer: CustomerResponseDTO,
    @SerializedName("confectioner") val confectioner: ConfectionerResponseDTO,
    @SerializedName("cake") val cake: CakeResponseDTO,
    @SerializedName("price") val price: Int,
    @SerializedName("status") val status: OrderStatus,
    @SerializedName("quantity") val quantity: Int,
    @SerializedName("created_at") val createdAt: Instant,
    @SerializedName("is_custom") val isCustom: Boolean
) {
    fun toOrder(customer: Customer, confectioner: Confectioner): Order {
        return Order(
            id = this.id,
            cake = if (this.cake.isCustom) this.cake.toCustom() else this.cake.toGeneral(),
            date = createdAt.toLocalDateTime(TimeZone.currentSystemDefault()).date,
            orderStatus = status,
            price = price,
            quantity = quantity,
            customer = customer,
            confectioner = confectioner
        )
    }
}