package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.core.models.Confectioner
import dev.tp_94.mobileapp.core.models.Customer
import dev.tp_94.mobileapp.core.models.Session
import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.UserPassword

interface UserRepository {
    suspend fun login(username: String, password: String): Session
    suspend fun add(confectioner: Confectioner, password: String): Session
    suspend fun add(customer: Customer, password: String): Session
    suspend fun update(user: User): User
}