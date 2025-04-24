package dev.tp_94.mobileapp.signup.presenatation

data class SignUpState(
    val isLoading: Boolean = false,
    val phoneNumber: String = "",
    val password: String = "",
    val name: String = "",
    val email: String = "",
    val error: String? = null
)
