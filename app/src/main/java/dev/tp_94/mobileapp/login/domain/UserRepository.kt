package dev.tp_94.mobileapp.login.domain

import dev.tp_94.mobileapp.core.models.User

interface UserRepository {
    suspend fun login(username: String, password: String): User
}