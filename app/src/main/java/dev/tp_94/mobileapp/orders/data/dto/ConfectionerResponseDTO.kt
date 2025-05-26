package dev.tp_94.mobileapp.orders.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.core.models.Confectioner

data class ConfectionerResponseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("description") val description: String,
    @SerializedName("address") val address: String
) {
    fun toConfectioner(): Confectioner {
        return Confectioner(
            id = this.id,
            name = this.name,
            phoneNumber = this.phone,
            email = this.email,
            description = this.description,
            address = this.address,
            //TODO: AWAIT BACKEND
            canWithdrawal = 0,
            inProcess = 0,
        )
    }
}
