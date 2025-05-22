package dev.tp_94.mobileapp.login.data.dto

data class UserResponseDTO(
    val id: Long,
    val phone: String,
    val email: String,
    val name: String,
    val type: String,
    val description: String?,
    val address: String?
)

