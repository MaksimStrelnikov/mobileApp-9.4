package dev.tp_94.mobileapp.login.data.dto

import com.google.gson.annotations.SerializedName

data class UserResponseWithTokensDTO(
    @SerializedName("id") override val id: Long,
    @SerializedName("phone") override val phone: String,
    @SerializedName("email") override val email: String,
    @SerializedName("name") override val name: String,
    @SerializedName("type") override val type: String,
    @SerializedName("description") override val description: String?,
    @SerializedName("address") override val address: String?,
    //TODO BACKEND AWAITING for withdrawal
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
): UserDTO

