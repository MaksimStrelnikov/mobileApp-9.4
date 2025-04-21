package dev.tp_94.mobileapp.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

sealed class User {
    abstract val id: Int
    abstract val name: String
    abstract val phoneNumber: String
    abstract val email: String
}

@JsonClass(generateAdapter = true)
@Json(name = "confectioner")
data class Confectioner(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val email: String,
    val description: String,
    val address: String
) : User()

@JsonClass(generateAdapter = true)
@Json(name = "customer")
data class Customer(
    override val id: Int,
    override val name: String,
    override val phoneNumber: String,
    override val email: String
) : User()
