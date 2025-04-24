package dev.tp_94.mobileapp.login.presentation

data class LoginState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val password: String = "",
    val error: String? = null
)
