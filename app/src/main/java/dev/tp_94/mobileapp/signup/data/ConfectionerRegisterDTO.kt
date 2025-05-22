package dev.tp_94.mobileapp.signup.data

data class ConfectionerRegisterDTO(
    val user_id: Long,
    val description: String,
    val address: String,
    val rating: Float = 0f,
    val max_diameter: Float = 0f,
    val min_diameter: Float = 0f,
    val entity: String? = null
)
