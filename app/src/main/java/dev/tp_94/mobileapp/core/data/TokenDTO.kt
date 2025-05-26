package dev.tp_94.mobileapp.core.data

import com.google.gson.annotations.SerializedName

data class TokenDTO(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String,
)
