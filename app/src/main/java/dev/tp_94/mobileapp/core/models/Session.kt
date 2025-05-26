package dev.tp_94.mobileapp.core.models

data class Session(
    val user: User,
    val accessToken: String,
    val refreshToken: String
)
