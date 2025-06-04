package dev.tp_94.mobileapp.profile_editor.data.dto

import com.google.gson.annotations.SerializedName

data class CustomerUpdateDTO(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String
)
