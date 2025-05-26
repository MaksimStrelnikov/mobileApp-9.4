package dev.tp_94.mobileapp.login.data.dto

import com.google.gson.annotations.SerializedName
import dev.tp_94.mobileapp.orders.data.dto.ConfectionerResponseDTO
import dev.tp_94.mobileapp.orders.data.dto.CustomerResponseDTO

data class UserResponseWithTokensDTO(
    @SerializedName("id") override val id: Long,
    @SerializedName("phone") override val phone: String,
    @SerializedName("email") override val email: String,
    @SerializedName("name") override val name: String,
    @SerializedName("type") override val type: String,
    @SerializedName("customer") override val customer: CustomerResponseDTO?,
    @SerializedName("confectioner") override val confectioner: ConfectionerResponseDTO?,
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
): UserDTO

