package dev.tp_94.mobileapp.login.presentation

import dev.tp_94.mobileapp.core.models.User

sealed class LoginResult {
    data class Success(val user: User) : LoginResult()
    data class Error(val message: String) : LoginResult()
}