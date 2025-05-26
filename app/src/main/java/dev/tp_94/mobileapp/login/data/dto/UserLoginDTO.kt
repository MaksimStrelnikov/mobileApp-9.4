package dev.tp_94.mobileapp.login.data.dto

import com.google.gson.annotations.SerializedName

data class UserLoginDTO(
    @SerializedName("phone") val phone: String,
    @SerializedName("password") val password: String
)