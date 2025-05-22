package dev.tp_94.mobileapp.signup.data

data class CustomerRegisterDTO(
    override val name: String,
    override val phone: String,
    override val password: String,
    override val email: String
): UserRegisterDTO
