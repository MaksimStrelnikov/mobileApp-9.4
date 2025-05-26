package dev.tp_94.mobileapp.login.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO
import dev.tp_94.mobileapp.orders.data.dto.CustomerResponseDTO

data class UserResponseDTO (
    @SerializedName("id") override val id: Long,
    @SerializedName("phone") override val phone: String,
    @SerializedName("email") override val email: String,
    @SerializedName("name") override val name: String,
    @SerializedName("type") override val type: String,
    @SerializedName("customer") val customer: CustomerResponseDTO?,
    @SerializedName("confectioner") val confectioner: ConfectionerResponseDTO?,
    //TODO BACKEND AWAITING for withdrawal
): UserDTO

interface UserDTO {
    val id: Long
    val phone: String
    val email: String
    val name: String
    val type: String
    //TODO BACKEND AWAITING for withdrawal
}
