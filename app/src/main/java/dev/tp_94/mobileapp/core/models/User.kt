package dev.tp_94.mobileapp.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.serialization.Serializable

@Serializable
sealed interface User {
    val id: Int
    val name: String
    val phoneNumber: String
    val email: String
}

@JsonClass(generateAdapter = true)
@Json(name = "confectioner")
@Serializable
data class Confectioner(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val email: String,
    val description: String,
    val address: String
) : User

@JsonClass(generateAdapter = true)
@Json(name = "customer")
@Serializable
data class Customer(
    override val id: Int,
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