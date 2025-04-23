package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.core.models.User
import dev.tp_94.mobileapp.core.models.UserPassword

interface UserRepository {
    suspend fun login(username: String, password: String): User
    suspend fun add(user: UserPassword): User
}