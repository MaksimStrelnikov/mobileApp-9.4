package dev.tp_94.mobileapp.signup.data

data class ConfectionerResponseDTO(
    override val name: String,
    val phone: String,
    val password: String,
    val email: String
): UserRegisterDTO(name, phone, password, email)
