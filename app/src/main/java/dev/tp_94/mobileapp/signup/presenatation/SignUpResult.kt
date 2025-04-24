package dev.tp_94.mobileapp.signup.presenatation

import dev.tp_94.mobileapp.core.models.User

sealed class SignUpResult {
    data class Success(val user: User) : SignUpResult()
    data class Error(val message: String) : SignUpResult()
}