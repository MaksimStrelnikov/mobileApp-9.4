package dev.tp_94.mobileapp.core.api.dto

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("token") val token: String
)
