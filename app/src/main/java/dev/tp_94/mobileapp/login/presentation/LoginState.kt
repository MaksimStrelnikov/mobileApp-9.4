package dev.tp_94.mobileapp.login.presentation

import dev.tp_94.mobileapp.core.models.User

sealed class LoginState {
    data class Success(val user: User) : LoginState()
    data class Error(val message: String) : LoginState()
}