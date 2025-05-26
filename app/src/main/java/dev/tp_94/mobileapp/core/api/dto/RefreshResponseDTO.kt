package dev.tp_94.mobileapp.core.api.dto

import com.google.gson.annotations.SerializedName

data class RefreshResponseDTO(
    @SerializedName("access_token") val accessToken: String,
    @SerializedName("refresh_token") val refreshToken: String
)
