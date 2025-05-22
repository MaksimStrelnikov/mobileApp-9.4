package dev.tp_94.mobileapp.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import dev.tp_94.mobileapp.login.data.ConfectionerRegisterDTO
import dev.tp_94.mobileapp.login.data.CustomerRegisterDTO
import dev.tp_94.mobileapp.login.data.UserRegisterDTO
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

fun UserPassword.toDto(): UserRegisterDTO {
    return UserRegisterDTO(
        name = name,
        phone_number = phoneNumber,
        password = password,
        email = email
    )
}

fun ConfectionerPassword.toDto(userId: Long): ConfectionerRegisterDTO {
    return ConfectionerRegisterDTO(
        user_id = userId,
        description = description,
        address = address
    )
}

fun CustomerPassword.toDto(userId: Long): CustomerRegisterDTO {
    return CustomerRegisterDTO(
        user_id = userId
    )
}
