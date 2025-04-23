package dev.tp_94.mobileapp.signup.presenatation

import dev.tp_94.mobileapp.core.models.User

sealed class SignUpState {
    data class Success(val user: User) : SignUpState()
    data class Error(val message: String) : SignUpState()
}