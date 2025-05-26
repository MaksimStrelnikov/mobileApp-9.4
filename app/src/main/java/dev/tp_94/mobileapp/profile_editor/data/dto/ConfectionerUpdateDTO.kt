package dev.tp_94.mobileapp.profile_editor.data.dto

import com.google.gson.annotations.SerializedName

data class ConfectionerUpdateDTO(
    @SerializedName("name") val name: String,
    @SerializedName("phone") val phone: String,
    @SerializedName("email") val email: String,
    @SerializedName("type") val type: String = "confectioner",
    @SerializedName("description") val description: String,
    @SerializedName("address") val address: String
)
