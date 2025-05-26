package dev.tp_94.mobileapp.signup.data

import com.google.gson.annotations.SerializedName

data class ConfectionerRegisterDTO(
    @SerializedName("name") override val name: String,
    @SerializedName("phone") override val phone: String,
    @SerializedName("password") override val password: String,
    @SerializedName("email") override val email: String,
    @SerializedName("address") val address: String
) : UserRegisterDTO
