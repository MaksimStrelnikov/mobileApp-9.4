package dev.tp_94.mobileapp.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.tp_94.mobileapp.login.data.dto.UserDTO
import dev.tp_94.mobileapp.login.data.dto.UserResponseWithTokensDTO
import dev.tp_94.mobileapp.signup.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.signup.data.CustomerRegisterDTO
import kotlinx.serialization.Serializable

@Serializable
sealed interface User {
    val id: Long
    val name: String
    val phoneNumber: String
    val email: String
}

@JsonClass(generateAdapter = true)
@Json(name = "confectioner")
@Serializable
data class Confectioner(
    override val id: Long,
    override val name: String,
    override val phoneNumber: String,
    override val email: String,
    val description: String,
    val address: String,
    val canWithdrawal: Int = 0,
    val inProcess: Int = 0
) : User

@JsonClass(generateAdapter = true)
@Json(name = "customer")
@Serializable
data class Customer(
    override val id: Long,
    override val name: String,
    override val phoneNumber: String,
    override val email: String
) : User

data class CustomerPassword(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val email: String,
    override val password: String
) : UserPassword

data class ConfectionerPassword(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val email: String,
    val description: String,
    val address: String,
    override val password: String
) : UserPassword

sealed interface UserPassword {
    val id: Int
    val password: String
    val name: String
    val phoneNumber: String
    val email: String
}

fun Confectioner.toDto(password: String): ConfectionerRegisterDTO {
    return ConfectionerRegisterDTO(
        name = name,
        phone = phoneNumber,
        password = password,
        email = email,
        address = address
    )
}

fun Customer.toDto(password: String): CustomerRegisterDTO {
    return CustomerRegisterDTO(
        name = name,
        phone = phoneNumber,
        password = password,
        email = email
    )
}

fun UserDTO.toConfectioner(): Confectioner {
    return this.confectioner!!.toConfectioner()
}

fun UserDTO.toCustomer(): Customer {
    return this.customer!!.toCustomer()
}
