package dev.tp_94.mobileapp.signup.data

import com.google.gson.annotations.SerializedName

data class CustomerRegisterDTO(
    @SerializedName("name") override val name: String,
    @SerializedName("phone") override val phone: String,
    @SerializedName("password") override val password: String,
    @SerializedName("type") val type: String = "customer",
    @SerializedName("email") override val email: String
) : UserRegisterDTO
