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
    @SerializedName("customer") override val customer: CustomerResponseDTO?,
    @SerializedName("confectioner") override val confectioner: ConfectionerResponseDTO?,
): UserDTO {
    fun toUserResponseWithTokensDTO(
        accessToken: String,
        refreshToken: String
    ): UserResponseWithTokensDTO = UserResponseWithTokensDTO(
        id = this.id,
        phone = this.phone,
        email = this.email,
        name = this.name,
        type = this.type,
        customer = this.customer,
        confectioner = this.confectioner,
        accessToken = accessToken,
        refreshToken = refreshToken
    )
}

interface UserDTO {
    val id: Long
    val phone: String
    val email: String
    val name: String
    val type: String
    val customer: CustomerResponseDTO?
    val confectioner: ConfectionerResponseDTO?
}
