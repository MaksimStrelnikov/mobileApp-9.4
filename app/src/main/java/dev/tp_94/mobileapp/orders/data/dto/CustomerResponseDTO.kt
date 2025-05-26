package dev.tp_94.mobileapp.orders.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.Customer

data class CustomerResponseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String
) {
    fun toCustomer(): Customer {
        return Customer(
            id = this.id,
            name = this.name,
            phoneNumber = this.phone,
            email = this.email
        )
    }
}
