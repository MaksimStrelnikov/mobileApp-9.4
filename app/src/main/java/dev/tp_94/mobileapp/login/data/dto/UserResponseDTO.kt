package dev.tp_94.mobileapp.login.data.dto

import com.google.gson.annotations.SerializedName

data class UserResponseDTO(
    @SerializedName("id") val id: Long,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("name") val name: String,
    @SerializedName("type") val type: String,
    @SerializedName("description") val description: String?,
    @SerializedName("address") val address: String?
)

