package dev.tp_94.mobileapp.login.data

data class UserResponseDTO(
    val id: Long,
    val email: String,
    val name: String,
    val password: String,
    val phone_number: String,
    val confectioner: ConfectionerResponseDTO,
    val customer: CustomerResponseDTO
)

